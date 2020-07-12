package com.example.postapp.ui.main.asyctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.postapp.ui.main.model.Item;
import com.example.postapp.ui.main.sqlite.room.ItemDao;
import com.example.postapp.ui.main.sqlite.room.ItemsDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ItemAsyncTask extends AsyncTask<Void, List<Item>, ArrayList<Item>> {

    private static final String  TOKEN = "A2DACF08-55F1-4D03-90B7-8839E66AE57A";
    private static final String  TAG = ItemAsyncTask.class.getSimpleName();

    private MutableLiveData<List<Item>> listItems;
    private StringBuilder bstrb = null;
    private Context context;


    public ItemAsyncTask(MutableLiveData<List<Item>> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    protected ArrayList<Item> doInBackground(Void... voids) {
        int count = 1;

        //creating database
        ItemsDatabase db = Room.databaseBuilder(context, ItemsDatabase.class, "items-db").fallbackToDestructiveMigration().build();

        ItemDao dao = db.movieDao();
        List daoList = dao.getAllMovies();


        //if the daoList not empty, present first the dao list and then fetch the internet changes
        //(if there is)
        if (daoList != null && daoList.size() > 0){
            publishProgress(daoList);
        }

        try {
            URL url = new URL("https://api.rivhit.co.il/online/RivhitOnlineAPI.svc/Item.List");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");


            JSONObject json = new JSONObject();
            try {
                json.put("api_token", TOKEN);

            } catch (JSONException e) {
                Log.d(TAG, "doInBackground: " + e);
                return null;
            }

            String input = json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bstrb = new StringBuilder();

            while ((output = br.readLine()) != null) {
                bstrb.append(output);
            }

            conn.disconnect();

            String ItemsJson = bstrb.toString();

            try {
                ArrayList<Item> results = new ArrayList();
                int itemId = 0;
                int quantity = 0;
                String itemName;
                String pictureLink;
                String costNis;

                JSONObject root = new JSONObject(ItemsJson);
                JSONObject dataObj = root.getJSONObject("data");
                JSONArray itemListArr = dataObj.getJSONArray("item_list");
                for (int i = 0; i < itemListArr.length(); i++) {
                    Log.d(TAG, "doInBackground:  i = " + i);
                    JSONObject itemListObj = (JSONObject) itemListArr.get(i);
                    itemId = itemListObj.getInt("item_id");
                    itemName = itemListObj.getString("item_name");
                    pictureLink = itemListObj.getString("picture_link");
                    costNis = itemListObj.getString("cost_nis");

                    Item item = new Item(itemId,itemName,pictureLink,costNis);
                    results.add(item);

                    //For Room
                    dao.insertAll(item);

                }
                return results;

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            Log.d("TAG", " MalformedURLException" + e.getMessage());
            return (ArrayList<Item>) daoList;
        } catch (UnsupportedEncodingException e) {
            Log.d("TAG", "Fail UnsupportedEncodingException" + e.getMessage());
            return (ArrayList<Item>) daoList;
        } catch (IllegalStateException e) {
            Log.d("TAG", "Fail IllegalStateException" + e.getMessage());
            return (ArrayList<Item>) daoList;
        } catch (IOException e) {
            Log.d("TAG", "Fail IOException" + e.getMessage());
            return (ArrayList<Item>) daoList;
        }
        return (ArrayList<Item>) daoList;
    }


    @Override
    protected void onProgressUpdate(List<Item>... values) {
        listItems.setValue(values[0]);
    }

    //update the UIThread on the change live data
    @Override
    protected void onPostExecute(ArrayList<Item> items) {
        super.onPostExecute(items);
        listItems.setValue(items);
    }


}

package com.example.postapp.ui.main.model;

import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ItemAsyncTask extends AsyncTask<Void, Integer, ArrayList<Item>> {

    private static final String  TOKEN = "A2DACF08-55F1-4D03-90B7-8839E66AE57A";
    private static final String  TAG = ItemAsyncTask.class.getSimpleName();

    private MutableLiveData<List<Item>> listItems;
    private StringBuilder bstrb = null;

    public ItemAsyncTask(MutableLiveData<List<Item>> listItems) {
        this.listItems = listItems;
    }

    @Override
    protected ArrayList<Item> doInBackground(Void... voids) {
        int count = 1;

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

                }
                return results;

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            Log.d("TAG", " MalformedURLException" + e.getMessage());
            return null;
        } catch (UnsupportedEncodingException e) {
            Log.d("TAG", "Fail UnsupportedEncodingException" + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            Log.d("TAG", "Fail IllegalStateException" + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.d("TAG", "Fail IOException" + e.getMessage());
            return null;
        }
        return null;
    }


    //update the UIThread on the change live data
    @Override
    protected void onPostExecute(ArrayList<Item> items) {
        super.onPostExecute(items);
        listItems.setValue(items);
    }


}

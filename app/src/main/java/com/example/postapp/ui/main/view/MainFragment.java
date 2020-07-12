package com.example.postapp.ui.main.view;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.postapp.R;
import com.example.postapp.ui.main.adapters.ItemAdapter;
import com.example.postapp.ui.main.asyctask.ItemAsyncTask;
import com.example.postapp.ui.main.sqlite.room.ItemsDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView headerSales;
    private TextView date;
    private EditText searchInput;
    private ItemAdapter itemAdapter;
    private boolean layoutNotChanged = true;

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        //set the search
        searchInput = view.findViewById(R.id.et_search);

        //set the current sales header
        setSalesTitle(view);

        //set the current date header
        setDateHeader(view);

        //set the recyclerview
        final RecyclerView recyclerView = view.findViewById(R.id.my_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);

        ImageButton changeLayout = view.findViewById(R.id.layout_btn);
        changeLayout.setOnClickListener(b->{
            if (layoutNotChanged){
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                changeLayout.setImageResource(R.drawable.ic_grid);
                layoutNotChanged = false;
            }else {
                recyclerView.setLayoutManager(gridLayoutManager);
                changeLayout.setImageResource(R.drawable.ic_list);
                layoutNotChanged = true;
            }

        });

        mViewModel.getmItems().observe(getViewLifecycleOwner(),(items) ->{
            itemAdapter = new ItemAdapter(items,getContext(),getLayoutInflater());
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(itemAdapter);


            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    Toast.makeText(getContext(), "Saved to Favorites", Toast.LENGTH_SHORT).show();

                }
            }).attachToRecyclerView(recyclerView);


            searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    itemAdapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });

        return view;
    }

    private void setSalesTitle(View view){
        headerSales = view.findViewById(R.id.text_grid);
        Random r = new Random();
        int low = 10000000;
        int high = 20000000;
        int result = r.nextInt(high-low) + low;
        String sales1 = String.valueOf(result).substring(0,2);
        String sales2 = String.valueOf(result).substring(2,5);
        String sales3 = String.valueOf(result).substring(5);
        headerSales.setText(sales1 + "," + sales2 + "," + sales3 +"$");
    }

    private void setDateHeader(View view){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date = view.findViewById(R.id.text_date);
        date.setText(formattedDate);
    }


}
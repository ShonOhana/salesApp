package com.example.postapp.ui.main.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.postapp.ui.main.model.Item;
import com.example.postapp.ui.main.model.ItemAsyncTask;

import java.util.List;

public class MainViewModel extends ViewModel {

    //add a mutable  liva data list of the object
    private MutableLiveData<List<Item>> mItems;

    public MainViewModel(){
        //create the instance of the MutableLivedata when ViewModel is called
        mItems = new MutableLiveData<>();

        //create an instance of the async class and execute it:
        ItemAsyncTask asyncTask = new ItemAsyncTask(mItems);
        asyncTask.execute();
    }

    //getter for the mutable list
    public MutableLiveData<List<Item>> getmItems() {
        return mItems;
    }
}
package com.example.postapp.ui.main.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class DetailFragment extends Fragment {

    private String image;
    private String itemCost;
    private String itemName;
    private TextView cost;
    private ImageView imageView;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        initialPage(view);

        //purchase button
        Button purchase = view.findViewById(R.id.btn_purchase);
        purchase.setOnClickListener(b->{
            openWebPage();
        });

        //set the fab
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(b->{
            Intent mailto = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","shon.ohana1@gmail.com", null));
            startActivity(Intent.createChooser(mailto, "Choose an Email client :"));
        });



        return view;
    }

    private void openWebPage() {
        Uri webpage = Uri.parse("https://www.easy-sale.co.il/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(intent);
    }

    private void initialPage(View view){
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("item_name") && intent.hasExtra("cost_nis") && intent.hasExtra("picture_link")){
            image = intent.getStringExtra("picture_link");
            itemCost = intent.getStringExtra("cost_nis");
            itemName = intent.getStringExtra("item_name");

            TextView name = view.findViewById(R.id.item_name_tv);
            name.setText(itemName);

            name.setOnClickListener(b->{
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(name.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent searchIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(searchIntent);
            });

            cost = view.findViewById(R.id.item_cost_tv);
            cost.setText(itemCost + " ₪ ");

            imageView = view.findViewById(R.id.big_image);
            Picasso.get().load(image).into(imageView);
        }
    }

}
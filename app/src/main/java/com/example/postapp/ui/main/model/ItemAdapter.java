package com.example.postapp.ui.main.model;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.R;
import com.example.postapp.ui.main.controller.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;
    private LayoutInflater inflater;

    public ItemAdapter(List<Item> itemList, Context context, LayoutInflater inflater) {
        this.itemList = itemList;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent , false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.itemName.setText(item.getItemName());


        if (item.getPictureLink() != null && !item.getPictureLink().equals("")) {
            Picasso.get()
                    .load(item.getPictureLink())
                    .placeholder(R.drawable.place_holderil)
                    .error(R.drawable.error)
                    .into(holder.itemImage);
        }

        holder.parentLayout.setOnClickListener(v->{

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("item_name", item.getItemName());
            intent.putExtra("cost_nis", item.getCostNis());
            intent.putExtra("picture_link", item.getPictureLink());

            Pair[] pairs = new Pair[3];

            pairs[0] = new Pair<View,String>(holder.itemImage,"imageTransition");
            pairs[1] = new Pair<View,String>(holder.itemName,"nameTransition");
            pairs[2] = new Pair<View,String>(holder.itemName,"costTransition");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,pairs);

            context.startActivity(intent,options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
         return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView itemImage;
        private TextView itemName;
        private CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

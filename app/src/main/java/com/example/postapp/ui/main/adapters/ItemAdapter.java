package com.example.postapp.ui.main.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.R;
import com.example.postapp.ui.main.activities.DetailActivity;
import com.example.postapp.ui.main.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {

    private List<Item> itemList;
    private List<Item> mDataFilter;
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

        //animation
        holder.itemImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_translate));
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));


        //init picture
        if (item.getPictureLink() != null && !item.getPictureLink().equals("")) {
            Picasso.get()
                    .load(item.getPictureLink())
                    .placeholder(R.drawable.place_holderil)
                    .error(R.drawable.error)
                    .into(holder.itemImage);
        }

        //Dialog init
        final Dialog detailDialog = new Dialog(context);
        detailDialog.setContentView(R.layout.item_dialog);
        Window window = detailDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        holder.parentLayout.setOnClickListener(v->{

            detailDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
            detailDialog.show();

            TextView dialogName = detailDialog.findViewById(R.id.dialog_name);
            TextView dialogPrice = detailDialog.findViewById(R.id.dialog_quantity);
            ImageView dialogImage = detailDialog.findViewById(R.id.dialog_image);
            Button purchaseBtn = detailDialog.findViewById(R.id.dialog_button_purchase);
            Button websiteBtn = detailDialog.findViewById(R.id.dialog_button_website);
            dialogName.setText(item.getItemName());
            dialogPrice.setText(item.getCostNis() + " ₪ ");
            purchaseBtn.setOnClickListener(b->{

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("item_name", item.getItemName());
                intent.putExtra("cost_nis", item.getCostNis());
                intent.putExtra("picture_link", item.getPictureLink());
                intent.putExtra("dialog", item.getPictureLink());

                Pair[] pairs = new Pair[3];

                pairs[0] = new Pair<View,String>(holder.itemImage,"imageTransition");
                pairs[1] = new Pair<View,String>(holder.itemName,"nameTransition");
                pairs[2] = new Pair<View,String>(holder.itemName,"costTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,pairs);

                context.startActivity(intent,options.toBundle());
                detailDialog.dismiss();
            });
            websiteBtn.setOnClickListener(b->{
                openWebPage();
            });

            if (item.getPictureLink() != null && !item.getPictureLink().equals("")){
                Picasso.get().load(item.getPictureLink()).fit().centerInside().into(dialogImage);
                detailDialog.show();
            }

        });
    }

    @Override
    public int getItemCount() {
         return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()) {
                    mDataFilter = itemList;
                } else {
                    List<Item> list_filtered = new ArrayList<>();
                    for (Item row : itemList) {
                        if (row.getItemName().toLowerCase().contains(key.toLowerCase())) {
                            list_filtered.add(row);
                        }
                    }

                    mDataFilter = list_filtered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFilter = (List<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
    private void openWebPage() {
        Uri webpage = Uri.parse("https://www.easy-sale.co.il/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        context.startActivity(intent);
    }
}

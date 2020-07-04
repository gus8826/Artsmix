package com.creaarte.creaarte.Layouts.MenuMain.MenuMain;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemCategory;
import com.creaarte.creaarte.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CellItemMainCategoriesAdapter extends RecyclerView.Adapter<CellItemMainCategoriesAdapter.ViewMainCategories> {

    private Activity activity;
    private List<ItemCategory> list;
    private LayoutInflater inflater;

    public CellItemMainCategoriesAdapter(Activity activity, List<ItemCategory> list) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewMainCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_item_main_categories_adapter, parent, false);
        return new ViewMainCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMainCategories holder, int position) {

        if (list.get(position).getCATG_name().isEmpty()) {
            holder.textViewNameMainCategoriesAdapter.setText("");
        } else {
            holder.textViewNameMainCategoriesAdapter.setText(list.get(position).getCATG_name());
        }

        if (list.get(position).getCATG_img().isEmpty()) {
            holder.imageViewImageLogoMainCategoriesAdapter.setImageResource(R.mipmap.ic_logo_icon_artmix);
        } else {
            Picasso.get().load(list.get(position).getCATG_img().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(holder.imageViewImageLogoMainCategoriesAdapter);
        }

        holder.imageViewImageLogoMainCategoriesAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.cardViewMainCategoriesAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewMainCategories extends RecyclerView.ViewHolder {
        CardView cardViewMainCategoriesAdapter;
        ImageView imageViewImageLogoMainCategoriesAdapter;
        TextView textViewNameMainCategoriesAdapter;

        ViewMainCategories(@NonNull View view) {
            super(view);
            cardViewMainCategoriesAdapter = view.findViewById(R.id.cardViewMainCategoriesAdapter);
            imageViewImageLogoMainCategoriesAdapter = view.findViewById(R.id.imageViewImageLogoMainCategoriesAdapter);
            textViewNameMainCategoriesAdapter = view.findViewById(R.id.textViewNameMainCategoriesAdapter);

        }
    }
}

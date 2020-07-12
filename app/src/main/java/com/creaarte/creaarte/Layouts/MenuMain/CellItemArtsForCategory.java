package com.creaarte.creaarte.Layouts.MenuMain;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemArts;
import com.creaarte.creaarte.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CellItemArtsForCategory extends RecyclerView.Adapter<CellItemArtsForCategory.ViewArstForCategoryHolder> {

    private Activity activity;
    private List<ItemArts> list;
    private LayoutInflater inflater;

    public CellItemArtsForCategory(Activity activity, List<ItemArts> list) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewArstForCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_item_main_categories_adapter, parent, false);
        return new CellItemArtsForCategory.ViewArstForCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewArstForCategoryHolder holder, int position) {
        if (list.get(position).getARTW_img().isEmpty()) {
            holder.ivArtsForCategory.setImageResource(R.mipmap.ic_logo_icon_artmix);
        } else {
            Picasso.get().load(list.get(position).getARTW_img().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(holder.ivArtsForCategory);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewArstForCategoryHolder extends RecyclerView.ViewHolder {
        CardView cvArtsForCategory;
        ImageView ivArtsForCategory;

        ViewArstForCategoryHolder(@NonNull View view) {
            super(view);
            cvArtsForCategory = view.findViewById(R.id.cvArtsForCategory);
            ivArtsForCategory = view.findViewById(R.id.ivArtsForCategory);
        }
    }
}
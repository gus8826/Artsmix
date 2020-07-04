package com.creaarte.creaarte.Layouts.MenuMain.MenuMain;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemArts;
import com.creaarte.creaarte.Models.ItemComments;
import com.creaarte.creaarte.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Objects;

public class CellItemArtsCommentsAdapter extends RecyclerView.Adapter<CellItemArtsCommentsAdapter.ViewArtsComments> {

    private Activity activity;
    private List<ItemComments> list;
    private LayoutInflater inflater;

    public CellItemArtsCommentsAdapter(Activity activity, List<ItemComments> list) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public CellItemArtsCommentsAdapter.ViewArtsComments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_item_arts_comments_adapter, parent, false);
        return new CellItemArtsCommentsAdapter.ViewArtsComments(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellItemArtsCommentsAdapter.ViewArtsComments holder, int position) {

        if (list.get(position).getCOMM_comm().isEmpty()) {
            holder.textViewCommentsArtsCommentsAdapter.setText("");
        } else {
            holder.textViewCommentsArtsCommentsAdapter.setText(list.get(position).getCOMM_comm());
        }

        /*if (list.get(position).getUSRL_img_url().isEmpty()) {
            holder.imageViewImageProfileArtsCommentsAdapter.setImageResource(R.mipmap.ic_logo_icon_artmix);
        } else {
            Picasso.with(activity).load(list.get(position).getUSRL_img_url().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(holder.imageViewImageProfileArtsCommentsAdapter);
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewArtsComments extends RecyclerView.ViewHolder {

        ImageView imageViewImageProfileArtsCommentsAdapter;
        TextView textViewCommentsArtsCommentsAdapter;

        ViewArtsComments(@NonNull View view) {
            super(view);
            imageViewImageProfileArtsCommentsAdapter = view.findViewById(R.id.imageViewImageProfileArtsCommentsAdapter);
            textViewCommentsArtsCommentsAdapter = view.findViewById(R.id.textViewCommentsArtsCommentsAdapter);
        }

    }

    private void showDialogRequest(TextView textView) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_request);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView textViewTitleDialogRequest = dialog.findViewById(R.id.textViewTitleDialogRequest);
        textViewTitleDialogRequest.setText(R.string.app_name);

        TextView textViewTextInfotmationDialogRequest = dialog.findViewById(R.id.textViewTextInfotmationDialogRequest);
        textViewTextInfotmationDialogRequest.setText(R.string.text_variable_69);

        Button buttonClosedDialogRequest = dialog.findViewById(R.id.buttonClosedDialogRequest);
        buttonClosedDialogRequest.setText(R.string.text_variable_71);
        buttonClosedDialogRequest.setOnClickListener(v -> dialog.dismiss());

        Button buttonAceptDialogRequest = dialog.findViewById(R.id.buttonAceptDialogRequest);
        buttonAceptDialogRequest.setText(R.string.text_variable_70);
        buttonAceptDialogRequest.setOnClickListener(v -> {
            Navigation.findNavController(textView).navigate(R.id.action_nav_menu_main_to_login_options_fragment);
            dialog.dismiss();
        });
        dialog.show();
    }
}
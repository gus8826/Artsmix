package com.creaarte.creaarte.Layouts.MenuMain.MenuMain;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemArts;
import com.creaarte.creaarte.Models.ItemLike;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Sets.SetReaction;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CellItemArtsAdapter extends RecyclerView.Adapter<CellItemArtsAdapter.ViewArts> {

    private Activity activity;
    private List<ItemArts> list;
    private LayoutInflater inflater;
    private AppCreaarte appCreaarte;
    private TableLoginUserInfo tableLoginUserInfo;
    private String ipAddress;
    private ItemLike itemLike;

    public CellItemArtsAdapter(Activity activity, List<ItemArts> list) {
        this.activity = activity;
        this.list = list;
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);
        itemLike = new ItemLike();
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewArts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_item_arts_adapter, parent, false);
        return new ViewArts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewArts holder, int position) {
        if (!tableLoginUserInfo.getUSRL_id().isEmpty() || !list.get(position).getARTW_id().isEmpty()) {
            if (list.get(position).getUSRL_alias().isEmpty()) {
                holder.textViewUserNameArtsAdapter.setText("");
            } else {
                holder.textViewUserNameArtsAdapter.setText(list.get(position).getUSRL_alias());
            }

            if (list.get(position).getARTW_name().isEmpty()) {
                holder.textViewArtsNameArtsAdapter.setText("");
            } else {
                holder.textViewArtsNameArtsAdapter.setText(list.get(position).getARTW_name());
            }

            if (list.get(position).getARTW_desc().isEmpty()) {
                holder.textViewArtsDescrptionArtsAdapter.setText("");
            } else {
                holder.textViewArtsDescrptionArtsAdapter.setText(list.get(position).getARTW_desc());
            }

            if (list.get(position).getARTW_reac().isEmpty()) {
                holder.textViewNumberLikeArtsAdapter.setText("");
            } else {
                holder.textViewNumberLikeArtsAdapter.setText(list.get(position).getARTW_reac());
            }

            if (list.get(position).getARTW_comm().isEmpty()) {
                holder.textViewNomberCommentsArtsAdapter.setText("");
            } else {
                holder.textViewNomberCommentsArtsAdapter.setText(list.get(position).getARTW_comm());
            }

            if (list.get(position).getUSRL_img_url().isEmpty()) {
                holder.imageViewArtistImageProfileArtsAdapter.setImageResource(R.mipmap.ic_logo_icon_artmix);
            } else {
                Picasso.get().load(list.get(position).getUSRL_img_url().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(holder.imageViewArtistImageProfileArtsAdapter);
            }

            if (list.get(position).getARTW_img().isEmpty()) {
                holder.imageViewArtsImageArtsAdapter.setImageResource(R.mipmap.ic_logo_icon_artmix);
            } else {
                Picasso.get().load(list.get(position).getARTW_img().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(holder.imageViewArtsImageArtsAdapter);
            }

            if (list.get(position).getARTW_reac_flag().equals("1")) {
                holder.buttonLikeArtsAdapter.setTextColor(activity.getColor(R.color.colorPrimary));
                holder.imageViewLikeArtsAdapter.setImageResource(R.mipmap.ic_heart_liked);
            }

            if (list.get(position).getARTW_reac_flag().equals("0")) {
                holder.buttonLikeArtsAdapter.setTextColor(activity.getColor(R.color.Grey900));
                holder.imageViewLikeArtsAdapter.setImageResource(R.mipmap.ic_heart_outline_grey600_24dp);
            }

            holder.buttonLikeArtsAdapter.setOnClickListener(view -> {
                if (!tableLoginUserInfo.getUSRL_id().isEmpty()) {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            try {
                                itemLike = new SetReaction(activity).execute("1", list.get(position).getARTW_id(), tableLoginUserInfo.getUSRL_id(), ipAddress).get();
                                if (itemLike.getARTW_reac_flag().equals("1")) {
                                    holder.buttonLikeArtsAdapter.setTextColor(activity.getColor(R.color.colorPrimary));
                                    holder.imageViewLikeArtsAdapter.setImageResource(R.mipmap.ic_heart_liked);
                                }

                                if (itemLike.getARTW_reac_flag().equals("0")) {
                                    holder.buttonLikeArtsAdapter.setTextColor(activity.getColor(R.color.Grey900));
                                    holder.imageViewLikeArtsAdapter.setImageResource(R.mipmap.ic_heart_outline_grey600_24dp);
                                }
                                holder.textViewNumberLikeArtsAdapter.setText(itemLike.getARTW_reac());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            appCreaarte.showToast(activity.getString(R.string.text_error_5));
                        }
                    } else {
                        appCreaarte.showToast(activity.getString(R.string.text_error_5));
                    }
                } else {
                    showDialogRequest(holder.textViewNomberCommentsArtsAdapter);
                }
            });

            holder.buttonCommentsArtsAdapter.setOnClickListener(view -> {
                if (!tableLoginUserInfo.getUSRL_id().isEmpty()) {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("id_article", list.get(position).getARTW_id());
                            Navigation.findNavController(view).navigate(R.id.action_nav_menu_main_to_nav_arts_comments_fragment, bundle);
                        } else {
                            appCreaarte.showToast(activity.getString(R.string.text_error_5));
                        }
                    } else {
                        appCreaarte.showToast(activity.getString(R.string.text_error_5));
                    }
                } else {
                    showDialogRequest(holder.textViewNomberCommentsArtsAdapter);
                }
            });

            holder.cardViewArtsAdapter.setOnClickListener(view -> {
                if (!tableLoginUserInfo.getUSRL_id().isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id_article", list.get(position).getARTW_id());
                    Navigation.findNavController(view).navigate(R.id.action_nav_menu_main_to_nav_art_details_fragment, bundle);
                } else {
                    showDialogRequest(holder.textViewNomberCommentsArtsAdapter);
                }
            });
        } else {
            showDialogRequest(holder.textViewNomberCommentsArtsAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewArts extends RecyclerView.ViewHolder {

        CardView cardViewArtsAdapter;
        ImageView imageViewLikeArtsAdapter;
        ImageView imageViewArtsImageArtsAdapter;
        ImageView imageViewArtistImageProfileArtsAdapter;
        TextView textViewUserNameArtsAdapter;
        TextView textViewArtsNameArtsAdapter;
        TextView textViewArtsDescrptionArtsAdapter;
        TextView textViewNumberLikeArtsAdapter;
        TextView textViewNomberCommentsArtsAdapter;
        Button buttonLikeArtsAdapter;
        Button buttonCommentsArtsAdapter;

        ViewArts(@NonNull View view) {
            super(view);
            cardViewArtsAdapter = view.findViewById(R.id.cardViewArtsAdapter);
            imageViewLikeArtsAdapter = view.findViewById(R.id.imageViewLikeArtsAdapter);
            imageViewArtsImageArtsAdapter = view.findViewById(R.id.imageViewArtsImageArtsAdapter);
            imageViewArtistImageProfileArtsAdapter = view.findViewById(R.id.imageViewArtistImageProfileArtsAdapter);
            textViewUserNameArtsAdapter = view.findViewById(R.id.textViewUserNameArtsAdapter);
            textViewArtsNameArtsAdapter = view.findViewById(R.id.textViewArtsNameArtsAdapter);
            textViewArtsDescrptionArtsAdapter = view.findViewById(R.id.textViewArtsDescrptionArtsAdapter);
            textViewNumberLikeArtsAdapter = view.findViewById(R.id.textViewNumberLikeArtsAdapter);
            textViewNomberCommentsArtsAdapter = view.findViewById(R.id.textViewNomberCommentsArtsAdapter);
            buttonLikeArtsAdapter = view.findViewById(R.id.buttonLikeArtsAdapter);
            buttonCommentsArtsAdapter = view.findViewById(R.id.buttonCommentsArtsAdapter);
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

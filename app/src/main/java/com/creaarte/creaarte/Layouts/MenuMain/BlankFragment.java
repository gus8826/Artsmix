package com.creaarte.creaarte.Layouts.MenuMain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemCategory;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.GetArticleByCategoryPag;

public class BlankFragment extends Fragment {

    private View viewBlankFragment;
    private Activity activity;
    private TableLoginUserInfo tableLoginUserInfo;
    private AppCreaarte appCreaarte;
    private String ipAddress = "";
    private RecyclerView rvArtsForCtagory;
    private ItemCategory itemCategory;

    public BlankFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBlankFragment = inflater.inflate(R.layout.fragment_blank, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);
        itemCategory = new ItemCategory();

        Bundle bundle = getArguments();
        if (bundle != null) {
            itemCategory = bundle.getParcelable("itemCategory");
        }
        Log.d("miomio", tableLoginUserInfo.getUSRL_id());
        rvArtsForCtagory = viewBlankFragment.findViewById(R.id.rvArtsForCtagory);
        rvArtsForCtagory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rvArtsForCtagory.setLayoutManager(layoutManager);

        if (appCreaarte.isNetDisponible()) {
            if (AppCreaarte.isOnlineNet()) {
                if (AppCreaarte.isConnectedWifi(activity)) {
                    ipAddress = appCreaarte.getDeviceWifiData();
                } else if (AppCreaarte.isConnectedMobile(activity)) {
                    ipAddress = appCreaarte.getDeviceipMobileData();
                }
                new GetArticleByCategoryPag(activity, rvArtsForCtagory).execute("100", "0", itemCategory.getCATG_id(), ipAddress, tableLoginUserInfo.getUSRL_id());
            } else {
                appCreaarte.showToast(getString(R.string.text_error_1));
            }
        } else {
            appCreaarte.showToast(getString(R.string.text_error_1));
        }

        return viewBlankFragment;
    }
}
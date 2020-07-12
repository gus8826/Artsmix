package com.creaarte.creaarte.Layouts.MenuMain.MenuMain;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Controllers.MyDividerItemDecoration;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Gets.GetArticlePag;
import com.creaarte.creaarte.WebService.Gets.GetCategoryPag;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MenuMainFragment extends Fragment {

    private View viewMenuMainFragment;
    private Activity activity;
    private TableLoginUserInfo tableLoginUserInfo;
    private AppCreaarte appCreaarte;
    private String ipAddress = "";
    //private String num_items = "20";
    //private String page = "0";
    //private String id_user = "";
    private TextView textViewTitleCategoriesMenuMain;
    private RecyclerView recyclerViewMainCategoriesMenuMain;
    private TextView textViewTitleArtsMenuMain;
    private RecyclerView recyclerViewMainArtsMenuMain;
    private ShimmerFrameLayout shimmerArtsMenuMain;
    private NestedScrollView nestedScrollViewMenuMain;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMenuMainFragment = inflater.inflate(R.layout.fragment_menu_main, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);

        textViewTitleCategoriesMenuMain = viewMenuMainFragment.findViewById(R.id.textViewTitleCategoriesMenuMain);
        textViewTitleArtsMenuMain = viewMenuMainFragment.findViewById(R.id.textViewTitleArtsMenuMain);
        shimmerArtsMenuMain = viewMenuMainFragment.findViewById(R.id.shimmerArtsMenuMain);
        nestedScrollViewMenuMain = viewMenuMainFragment.findViewById(R.id.nestedScrollViewMenuMain);
        recyclerViewMainCategoriesMenuMain = viewMenuMainFragment.findViewById(R.id.recyclerViewMainCategoriesMenuMain);
        recyclerViewMainArtsMenuMain = viewMenuMainFragment.findViewById(R.id.recyclerViewMainArtsMenuMain);

        nestedScrollViewMenuMain.setVisibility(View.GONE);
        recyclerViewMainCategoriesMenuMain.setVisibility(View.GONE);
        recyclerViewMainArtsMenuMain.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMainCategoriesMenuMain.setLayoutManager(layoutManager);

        shimmerArtsMenuMain.startShimmer();
        recyclerViewMainArtsMenuMain.setVisibility(View.GONE);
        recyclerViewMainArtsMenuMain.setHasFixedSize(true);
        recyclerViewMainArtsMenuMain.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewMainArtsMenuMain.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMainArtsMenuMain.addItemDecoration(new MyDividerItemDecoration(activity, LinearLayoutManager.VERTICAL, 16));

        getArticlePag();

        return viewMenuMainFragment;
    }

    private void getArticlePag() {
        shimmerArtsMenuMain.startShimmer();
        Thread artsTread = new Thread() {
            @Override
            public void run() {
                if (appCreaarte.isNetDisponible()) {
                    if (AppCreaarte.isOnlineNet()) {
                        if (AppCreaarte.isConnectedWifi(activity)) {
                            ipAddress = appCreaarte.getDeviceWifiData();
                        } else if (AppCreaarte.isConnectedMobile(activity)) {
                            ipAddress = appCreaarte.getDeviceipMobileData();
                        }
                        new GetCategoryPag(activity, textViewTitleCategoriesMenuMain, recyclerViewMainCategoriesMenuMain, nestedScrollViewMenuMain).execute("100", "0", ipAddress, tableLoginUserInfo.getUSRL_id(), "0");
                        new GetArticlePag(activity, textViewTitleArtsMenuMain, recyclerViewMainArtsMenuMain, nestedScrollViewMenuMain).execute("20", "1", ipAddress, tableLoginUserInfo.getUSRL_id());
                    } else {
                        activity.runOnUiThread(() -> {
                            appCreaarte.showToast(getString(R.string.text_error_1));
                        });
                    }
                } else {
                    activity.runOnUiThread(() -> {
                        appCreaarte.showToast(getString(R.string.text_error_1));
                    });
                }

                for (int i = 1; i <= 3; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                activity.runOnUiThread(() -> {
                    shimmerArtsMenuMain.stopShimmer();
                    shimmerArtsMenuMain.setVisibility(View.GONE);
                });

            }
        };
        artsTread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerArtsMenuMain.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerArtsMenuMain.stopShimmer();
        super.onPause();
    }

}

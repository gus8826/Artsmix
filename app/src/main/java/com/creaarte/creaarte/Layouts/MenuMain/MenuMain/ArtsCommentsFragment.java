package com.creaarte.creaarte.Layouts.MenuMain.MenuMain;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Controllers.MyDividerItemDecoration;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Gets.GetCommentsPag;
import com.creaarte.creaarte.WebService.Sets.SetComment;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Objects;

public class ArtsCommentsFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private TableLoginUserInfo tableLoginUserInfo;
    private AppCreaarte appCreaarte;
    private View viewArtsCommentsFragment;
    private RecyclerView recyclerViewArtsComments;
    private LinearLayout linearLayoutArtsComments;
    //private ImageView imageViewImageProfileArtsComments;
    private EditText editTextCommentArtsComments;
    private Button buttonCommentArtsComments;

    private String ipAddress = "";
    private String id_article = "";
    private ShimmerFrameLayout shimmerArtsMenuMain;
    private Thread artsTread;

    public ArtsCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewArtsCommentsFragment = inflater.inflate(R.layout.fragment_arts_comments, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);

        recyclerViewArtsComments = viewArtsCommentsFragment.findViewById(R.id.recyclerViewArtsComments);
        linearLayoutArtsComments = viewArtsCommentsFragment.findViewById(R.id.linearLayoutArtsComments);
        //imageViewImageProfileArtsComments = viewArtsCommentsFragment.findViewById(R.id.imageViewImageProfileArtsComments);
        editTextCommentArtsComments = viewArtsCommentsFragment.findViewById(R.id.editTextCommentArtsComments);
        buttonCommentArtsComments = viewArtsCommentsFragment.findViewById(R.id.buttonCommentArtsComments);
        buttonCommentArtsComments.setOnClickListener(this);

        recyclerViewArtsComments.setHasFixedSize(true);
        recyclerViewArtsComments.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewArtsComments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewArtsComments.addItemDecoration(new MyDividerItemDecoration(activity, LinearLayoutManager.VERTICAL, 16));

        getCoomentsPag();

        Bundle bundle = getArguments();
        if (bundle != null) {
            id_article = bundle.getString("id_article");
        }
        return viewArtsCommentsFragment;
    }

    private void getCoomentsPag() {
        //shimmerArtsMenuMain.startShimmer();
        artsTread = new Thread() {
            @Override
            public void run() {
                if (appCreaarte.isNetDisponible()) {
                    if (AppCreaarte.isOnlineNet()) {
                        if (AppCreaarte.isConnectedWifi(activity)) {
                            ipAddress = appCreaarte.getDeviceWifiData();
                        } else if (AppCreaarte.isConnectedMobile(activity)) {
                            ipAddress = appCreaarte.getDeviceipMobileData();
                        }
                        new GetCommentsPag(activity, recyclerViewArtsComments).execute("200", "1", ipAddress, tableLoginUserInfo.getUSRL_id(), id_article);
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_5));
                    }
                } else {
                    appCreaarte.showToast(getString(R.string.text_error_5));
                }

                for (int i = 1; i <= 1; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                activity.runOnUiThread(() -> {
                    //shimmerArtsMenuMain.stopShimmer();
                    //shimmerArtsMenuMain.setVisibility(View.GONE);
                });

            }
        };
        artsTread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCommentArtsComments:
                if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(editTextCommentArtsComments.getText()).toString())) {
                    editTextCommentArtsComments.setError(getString(R.string.text_error_web_servicies_40));
                    editTextCommentArtsComments.requestFocus();
                } else {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            new SetComment(activity).execute(editTextCommentArtsComments.getText().toString(), id_article, tableLoginUserInfo.getUSRL_id(), ipAddress);
                            new GetCommentsPag(activity, recyclerViewArtsComments).execute("200", "1", ipAddress, tableLoginUserInfo.getUSRL_id(), id_article);
                        } else {
                            appCreaarte.showToast(getString(R.string.text_error_5));
                        }
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_5));
                    }
                }

            default:
                break;

        }
    }
}

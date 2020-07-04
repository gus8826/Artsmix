package com.creaarte.creaarte.Layouts.MenuMain.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Controllers.CircleTransform;
import com.creaarte.creaarte.Layouts.MenuMain.Perfil.ui.SectionsPagerProfileUserAdapter;
import com.creaarte.creaarte.Models.ItemUserDate;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Gets.GetUserById;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileUserFragment extends Fragment implements View.OnClickListener {

    private String ipAddress = "";
    private ItemUserDate itemUserDate = new ItemUserDate();
    //private static final String TAG = "ProfileUserFragment";
    FragmentManager fm;
    public ProfileUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewProfileUserFragment = inflater.inflate(R.layout.fragment_profile_user, container, false);
        Activity activity = getActivity();
        fm = getFragmentManager();
        AppCreaarte appCreaarte = new AppCreaarte(activity);
        TableLoginUserInfo tableLoginUserInfo = new TableLoginUserInfo(activity);

        assert activity != null;
        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }

        try {
            itemUserDate = new GetUserById(activity).execute(tableLoginUserInfo.getUSRL_id(), ipAddress).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView imageViewProfileUser = viewProfileUserFragment.findViewById(R.id.imageViewProfileUser);
        TextView textViewNameProfileUser = viewProfileUserFragment.findViewById(R.id.textViewNameProfileUser);
        TextView textViewEmailProfileUser = viewProfileUserFragment.findViewById(R.id.textViewEmailProfileUser);
        Button buttonEditProfileUser = viewProfileUserFragment.findViewById(R.id.buttonEditProfileUser);
        buttonEditProfileUser.setOnClickListener(this);

        SectionsPagerProfileUserAdapter sectionsPagerAdapter = new SectionsPagerProfileUserAdapter(activity, Objects.requireNonNull(fm));
        ViewPager viewPager = viewProfileUserFragment.findViewById(R.id.viewPagerProfileUser);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = viewProfileUserFragment.findViewById(R.id.tabLayoutProfileUser);
        tabs.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.cards_heart);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.animation);

        if (!itemUserDate.getUSRL_name().isEmpty()) {
            textViewNameProfileUser.setText(itemUserDate.getUSRL_name());
        } else {
            textViewNameProfileUser.setText(R.string.nav_header_title);
        }

        if (!itemUserDate.getUSRL_email().isEmpty()) {
            textViewEmailProfileUser.setText(itemUserDate.getUSRL_email());
        } else {
            textViewEmailProfileUser.setText(R.string.nav_header_subtitle);
        }

        if (!itemUserDate.getUSRL_img_url().isEmpty()) {
            Picasso.get().load(itemUserDate.getUSRL_img_url().replace("..", AppCreaarte.BASE_IMAGE_URL)).transform(new CircleTransform()).resize(150, 150).into(imageViewProfileUser);
        }

        return viewProfileUserFragment;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonEditProfileUser) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("itemUserDate", itemUserDate);
            Navigation.findNavController(view).navigate(R.id.action_nav_profile_user_fragment_to_nav_update_profile_user_fragment, bundle);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : fm.getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}

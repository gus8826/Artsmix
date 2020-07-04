package com.creaarte.creaarte.Layouts.MenuMain.Perfil.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.creaarte.creaarte.Layouts.MenuMain.Perfil.FavoriteArtsFragment;
import com.creaarte.creaarte.Layouts.MenuMain.Perfil.MyArtsFragment;
import com.creaarte.creaarte.R;

public class PlaceHolderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment newInstance (int index){
        Fragment fragment = null;
        switch (index){
            case 1:fragment = new FavoriteArtsFragment();break;
            case 2:fragment = new MyArtsFragment();break;
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
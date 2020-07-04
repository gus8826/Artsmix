package com.creaarte.creaarte.Layouts.MenuMain.Perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creaarte.creaarte.R;


public class FavoriteArtsFragment extends Fragment {


    public FavoriteArtsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_arts, container, false);
    }
}

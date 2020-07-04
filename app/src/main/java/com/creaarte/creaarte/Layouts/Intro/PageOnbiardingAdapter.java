package com.creaarte.creaarte.Layouts.Intro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.creaarte.creaarte.Models.ItemIntro;
import com.creaarte.creaarte.R;

import java.util.List;
import java.util.Objects;

public class PageOnbiardingAdapter extends PagerAdapter {

    private Context mContext;
    private List<ItemIntro> mListScreen;

    PageOnbiardingAdapter(Context mContext, List<ItemIntro> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layoutScreen = Objects.requireNonNull(inflater).inflate(R.layout.page_onbiarding_adapter, null);

        ImageView imageViewOnbiardingPageAdapter = layoutScreen.findViewById(R.id.imageViewOnbiardingPageAdapter);
        TextView textViewTitleOnbiardingPageAdapter = layoutScreen.findViewById(R.id.textViewTitleOnbiardingPageAdapter);
        TextView textViewDescriptionsOnbiardingPageAdapter_1 = layoutScreen.findViewById(R.id.textViewDescriptionsOnbiardingPageAdapter_1);
        TextView textViewDescriptionsOnbiardingPageAdapter2 = layoutScreen.findViewById(R.id.textViewDescriptionsOnbiardingPageAdapter2);
        TextView textViewDescriptionsOnbiardingPageAdapter_3 = layoutScreen.findViewById(R.id.textViewDescriptionsOnbiardingPageAdapter_3);
        TextView textViewDescriptionsOnbiardingPageAdapter_4 = layoutScreen.findViewById(R.id.textViewDescriptionsOnbiardingPageAdapter_4);

        textViewTitleOnbiardingPageAdapter.setText(mListScreen.get(position).getTitle());
        textViewDescriptionsOnbiardingPageAdapter_1.setText(mListScreen.get(position).getDescription_1());
        textViewDescriptionsOnbiardingPageAdapter2.setText(mListScreen.get(position).getDescription_2());
        textViewDescriptionsOnbiardingPageAdapter_3.setText(mListScreen.get(position).getDescription_3());
        textViewDescriptionsOnbiardingPageAdapter_4.setText(mListScreen.get(position).getDescription_4());

        imageViewOnbiardingPageAdapter.setImageResource(mListScreen.get(position).getScreenImg());
        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
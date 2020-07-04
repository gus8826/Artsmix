package com.creaarte.creaarte.Layouts.MenuMain.Perfil.UserProfile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.creaarte.creaarte.Models.ItemGender;
import com.creaarte.creaarte.R;
import java.util.List;

public class GenderAdapter extends ArrayAdapter<ItemGender> {

    public GenderAdapter(Activity activity, List<ItemGender> list) {
        super(activity, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_gender_adapter, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.text_view_gender);
        ItemGender currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getGEND_desc());
        }
        return convertView;
    }
}

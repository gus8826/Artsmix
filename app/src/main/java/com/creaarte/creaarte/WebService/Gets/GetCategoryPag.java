package com.creaarte.creaarte.WebService.Gets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Layouts.MenuMain.MenuMain.CellItemMainCategoriesAdapter;
import com.creaarte.creaarte.Models.ItemCategory;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCategoryPag extends AsyncTask<String, String, List<ItemCategory>> {

    private int code = 0;
    private String error = "";
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerViewMainCategoriesMenuMain;
    private AppCreaarte appCreaarte;
    @SuppressLint("StaticFieldLeak")
    private TextView textViewTitleCategoriesMenuMain;
    @SuppressLint("StaticFieldLeak")
    private NestedScrollView nestedScrollViewMenuMain;

    public GetCategoryPag(Activity activity, TextView textViewTitleCategoriesMenuMain, RecyclerView recyclerViewMainCategoriesMenuMain, NestedScrollView nestedScrollViewMenuMain) {
        this.activity = activity;
        this.textViewTitleCategoriesMenuMain = textViewTitleCategoriesMenuMain;
        this.recyclerViewMainCategoriesMenuMain = recyclerViewMainCategoriesMenuMain;
        this.nestedScrollViewMenuMain = nestedScrollViewMenuMain;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ItemCategory> doInBackground(String... params) {
        List<ItemCategory> list = new ArrayList<>();
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "getCategoryPag");
        client.addTextParam("num_items", params[0]);
        client.addTextParam("page", params[1]);
        client.addTextParam("ipAddress", params[2]);
        client.addTextParam("id_user", params[3]);
        client.addTextParam("id_fath", params[4]);
        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONArray array = new JSONArray(aux);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ItemCategory itemCategory = new ItemCategory();
                            itemCategory.setCATG_id(object.getString("CATG_id"));
                            itemCategory.setCATG_name(object.getString("CATG_name"));
                            itemCategory.setCATG_desc(object.getString("CATG_desc"));
                            itemCategory.setCATG_cat_id(object.getString("CATG_cat_id"));
                            itemCategory.setCATG_img(object.getString("CATG_img"));
                            itemCategory.setCATG_dt_crea(object.getString("CATG_dt_crea"));
                            itemCategory.setCATG_dt_modf(object.getString("CATG_dt_modf"));
                            itemCategory.setUSRL_qualif_out_nmbr(object.getString("USRL_qualif_out_nmbr"));
                            itemCategory.setCATG_s(object.getString("CATG_s"));
                            itemCategory.setCATG_c(object.getString("CATG_c"));
                            itemCategory.setNumItems(object.getString("numItems"));
                            itemCategory.setTotalPage(object.getString("totalPage"));
                            itemCategory.setPage(object.getString("page"));
                            list.add(itemCategory);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (code == 400) {
                    try {
                        JSONObject objectLogin = new JSONObject(aux);
                        error = objectLogin.getString("Error");
                        Log.d(error, error);
                        ItemError itemError = new ItemError();
                        itemError.setMessage(error, activity);
                        error = itemError.getMessage();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!AppCreaarte.isOnlineNet()) {
                code = 500;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<ItemCategory> list) {
        super.onPostExecute(list);
        if (code == 200) {
            CellItemMainCategoriesAdapter cellItemMainCategoriesAdapter = new CellItemMainCategoriesAdapter(activity, list);
            recyclerViewMainCategoriesMenuMain.setAdapter(cellItemMainCategoriesAdapter);
            recyclerViewMainCategoriesMenuMain.setVisibility(View.VISIBLE);
            textViewTitleCategoriesMenuMain.setVisibility(View.VISIBLE);
            nestedScrollViewMenuMain.setVisibility(View.VISIBLE);
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_1));
        }
    }
}

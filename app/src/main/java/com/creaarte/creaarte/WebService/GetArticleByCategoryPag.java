package com.creaarte.creaarte.WebService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Layouts.MenuMain.CellItemArtsForCategory;
import com.creaarte.creaarte.Layouts.MenuMain.MenuMain.CellItemArtsAdapter;
import com.creaarte.creaarte.Layouts.MenuMain.MenuMain.CellItemMainCategoriesAdapter;
import com.creaarte.creaarte.Models.ItemArts;
import com.creaarte.creaarte.Models.ItemCategory;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetArticleByCategoryPag extends AsyncTask<String, String, List<ItemArts>> {

    private int code = 0;
    private String error = "";
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private List<ItemArts> list;
    private AppCreaarte appCreaarte;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerViewMainArtsMenuMain;
    //@SuppressLint("StaticFieldLeak")
    //private TextView textViewTitleArtsMenuMain;
    //@SuppressLint("StaticFieldLeak")
    //private NestedScrollView nestedScrollViewMenuMain;

    public GetArticleByCategoryPag(Activity activity/*, TextView textViewTitleArtsMenuMain*/, RecyclerView recyclerViewMainArtsMenuMain/*, NestedScrollView nestedScrollViewMenuMain*/) {
        this.activity = activity;
        //this.textViewTitleArtsMenuMain = textViewTitleArtsMenuMain;
        this.recyclerViewMainArtsMenuMain = recyclerViewMainArtsMenuMain;
        //this.nestedScrollViewMenuMain = nestedScrollViewMenuMain;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ItemArts> doInBackground(String... params) {
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "getArticleByCategoryPag");
        client.addTextParam("num_items", params[0]);
        client.addTextParam("page", params[1]);
        client.addTextParam("id_cat", params[2]);
        client.addTextParam("ipAddress", params[3]);
        client.addTextParam("id_user", params[4]);
        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONArray array = new JSONArray(aux);
                        list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ItemArts itemArts = new ItemArts();
                            itemArts.setARTW_id(object.getString("ARTW_id"));
                            itemArts.setUSRL_id(object.getString("USRL_id"));
                            itemArts.setUSRL_alias(object.getString("USRL_alias"));
                            itemArts.setUSRL_name(object.getString("USRL_name"));
                            itemArts.setUSRL_img_url(object.getString("USRL_img_url"));
                            itemArts.setARTW_name(object.getString("ARTW_name"));
                            itemArts.setARTW_sku(object.getString("ARTW_sku"));
                            itemArts.setARTW_desc(object.getString("ARTW_desc"));
                            itemArts.setARTW_img(object.getString("ARTW_img"));
                            itemArts.setCATG_id(object.getString("CATG_id"));
                            itemArts.setCATG_name(object.getString("CATG_name"));
                            itemArts.setARTW_avail(object.getString("ARTW_avail"));
                            itemArts.setARTW_dt_crea(object.getString("ARTW_dt_crea"));
                            itemArts.setARTW_dt_modf(object.getString("ARTW_dt_modf"));
                            itemArts.setARTW_tax1(object.getString("ARTW_tax1"));
                            itemArts.setARTW_tax2(object.getString("ARTW_tax2"));
                            itemArts.setARTW_tax3(object.getString("ARTW_tax3"));
                            itemArts.setARTW_cost(object.getString("ARTW_cost"));
                            itemArts.setARTW_price(object.getString("ARTW_price"));
                            itemArts.setARTW_comm(object.getString("ARTW_comm"));
                            itemArts.setARTW_reac(object.getString("ARTW_reac"));
                            itemArts.setARTW_reac_flag(object.getString("ARTW_reac_flag"));
                            itemArts.setARTW_qualif(object.getString("ARTW_qualif"));
                            itemArts.setCURR_id(object.getString("CURR_id"));
                            itemArts.setCURR_desc(object.getString("CURR_desc"));
                            itemArts.setARTW_pop(object.getString("ARTW_pop"));
                            itemArts.setARTW_grade_number(object.getString("ARTW_grade_number"));
                            itemArts.setARTW_grade_avg(object.getString("ARTW_grade_avg"));
                            itemArts.setNumItems(object.getString("numItems"));
                            itemArts.setTotalPage(object.getString("totalPage"));
                            itemArts.setPage(object.getString("page"));
                            list.add(itemArts);
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
    protected void onPostExecute(List<ItemArts> list) {
        super.onPostExecute(list);
        if (code == 200) {
            CellItemArtsForCategory cellItemArtsForCategory = new CellItemArtsForCategory(activity, list);
            recyclerViewMainArtsMenuMain.setAdapter(cellItemArtsForCategory);
            recyclerViewMainArtsMenuMain.setVisibility(View.VISIBLE);
            //textViewTitleArtsMenuMain.setVisibility(View.VISIBLE);
            //nestedScrollViewMenuMain.setVisibility(View.VISIBLE);
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_1));
        }

    }

}

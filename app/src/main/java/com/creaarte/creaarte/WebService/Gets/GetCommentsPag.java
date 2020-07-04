package com.creaarte.creaarte.WebService.Gets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Layouts.MenuMain.MenuMain.CellItemArtsCommentsAdapter;
import com.creaarte.creaarte.Models.ItemComments;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCommentsPag extends AsyncTask<String, String, List<ItemComments>> {

    private int code = 0;
    private String error = "";
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private List<ItemComments> list;
    private AppCreaarte appCreaarte;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerViewArtsComments;

    public GetCommentsPag(Activity activity, RecyclerView recyclerViewArtsComments) {
        this.activity = activity;
        this.recyclerViewArtsComments = recyclerViewArtsComments;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ItemComments> doInBackground(String... params) {
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "GetCommentsPag");
        client.addTextParam("num_items", params[0]);
        client.addTextParam("page", params[1]);
        client.addTextParam("ipAddress", params[2]);
        client.addTextParam("id_user", params[3]);
        client.addTextParam("ARTW_id",params[4]);

        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONArray array = new JSONArray(aux);
                        list = new ArrayList<>();
                        Log.d("getComments", "doInBackground: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ItemComments itemComments = new ItemComments();
                            itemComments.setCOMM_id(object.getString("COMM_id"));
                            itemComments.setARTW_id(object.getString("ARTW_id"));
                            itemComments.setUSRL_id(object.getString("USRL_id"));
                            itemComments.setCOMM_comm(object.getString("COMM_comm"));
                            itemComments.setCOMM_dt_crea(object.getString("COMM_dt_crea"));
                            itemComments.setCOMM_dt_modf(object.getString("COMM_dt_modf"));
                            itemComments.setCOMM_s(object.getString("COMM_s"));
                            itemComments.setCOMM_c(object.getString("COMM_c"));
                            list.add(itemComments);
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
    protected void onPostExecute(List<ItemComments> list) {
        super.onPostExecute(list);
        if (code == 200) {
            CellItemArtsCommentsAdapter cellItemArtsCommentsAdapter = new CellItemArtsCommentsAdapter(activity, list);
            recyclerViewArtsComments.setAdapter(cellItemArtsCommentsAdapter);
            //recyclerViewMainArtsMenuMain.setVisibility(View.VISIBLE);
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_5));
        }

    }

}

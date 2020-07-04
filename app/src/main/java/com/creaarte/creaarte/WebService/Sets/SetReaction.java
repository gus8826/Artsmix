package com.creaarte.creaarte.WebService.Sets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.Models.ItemLike;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class SetReaction extends AsyncTask<String, String, ItemLike> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private AppCreaarte appCreaarte;
    private int code = 500;
    private String error = "";

    public SetReaction(Activity activity) {
        this.activity = activity;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ItemLike doInBackground(String... params) {

        ItemLike itemLike = new ItemLike();

        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "setReaction");

        client.addTextParam("idTypeReact", params[0]);
        client.addTextParam("id_article", params[1]);
        client.addTextParam("id_user", params[2]);
        client.addTextParam("ipAddress", params[3]);

        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONObject object = new JSONObject(aux);
                        itemLike.setARTW_reac_flag(object.getString("ARTW_reac_flag"));
                        itemLike.setARTW_reac(object.getString("ARTW_reac"));
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
        return itemLike;
    }

    @Override
    protected void onPostExecute(ItemLike itemLike) {
        super.onPostExecute(itemLike);
        if (code == 200) {
            //appCreaarte.showToast(activity.getString(R.string.text_variable_72));
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_5));
        }
    }
}
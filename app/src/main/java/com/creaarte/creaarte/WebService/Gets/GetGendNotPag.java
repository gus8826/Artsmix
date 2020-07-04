package com.creaarte.creaarte.WebService.Gets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.widget.Spinner;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Layouts.MenuMain.Perfil.UserProfile.GenderAdapter;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.Models.ItemGender;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetGendNotPag extends AsyncTask<String, String, List<ItemGender>> {

    private int code = 0;
    private String error = "";
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    private Spinner spinnerGenderUpdateProfileUser;
    private Dialog dialog;
    private List<ItemGender> list;
    private AppCreaarte appCreaarte;

    public GetGendNotPag(Activity activity, Spinner spinnerGenderUpdateProfileUser) {
        this.activity = activity;
        this.spinnerGenderUpdateProfileUser = spinnerGenderUpdateProfileUser;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new Dialog(activity, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_charger);
        dialog.show();
    }

    @Override
    protected List<ItemGender> doInBackground(String... params) {

        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "getGendNotPag");
        client.addTextParam("id_user", params[0]);
        client.addTextParam("USRL_ip_addr", params[1]);

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
                            ItemGender itemGender = new ItemGender();
                            itemGender.setGEND_id(object.getString("GEND_id"));
                            itemGender.setGEND_desc(object.getString("GEND_desc"));
                            itemGender.setGEND_dt_crea(object.getString("GEND_dt_crea"));
                            itemGender.setGEND_dt_modf(object.getString("GEND_dt_modf"));
                            itemGender.setGEND_s(object.getString("GEND_s"));
                            itemGender.setGEND_c(object.getString("GEND_c"));
                            list.add(itemGender);
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
    protected void onPostExecute(List<ItemGender> list) {
        super.onPostExecute(list);
        if (code == 200) {
            GenderAdapter genderAdapter = new GenderAdapter(activity, list);
            spinnerGenderUpdateProfileUser.setAdapter(genderAdapter);
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_1));
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

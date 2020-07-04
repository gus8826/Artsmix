package com.creaarte.creaarte.WebService.Gets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.Models.ItemUserDate;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class GetUserById extends AsyncTask<String, String, ItemUserDate> {

    private int code = 0;
    private String error = "";
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private Dialog dialog;
    private AppCreaarte appCreaarte;

    public GetUserById(Activity activity) {
        this.activity = activity;
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
    protected ItemUserDate doInBackground(String... params) {
        ItemUserDate itemUserDate = new ItemUserDate();
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "getUserById");
        client.addTextParam("id_user", params[0]);
        client.addTextParam("ipAddress", params[1]);
        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONObject object = new JSONObject(aux);
                        Log.d("jsonWorkSpace", object.toString());
                        itemUserDate.setUSRL_id(object.getString("USRL_id"));
                        itemUserDate.setUSRT_id(object.getString("USRT_id"));
                        itemUserDate.setGEND_id(object.getString("GEND_id"));
                        itemUserDate.setUSRR_id(object.getString("USRR_id"));
                        itemUserDate.setUSRL_name(object.getString("USRL_name"));
                        itemUserDate.setUSRL_lfnm(object.getString("USRL_lfnm"));
                        itemUserDate.setUSRL_lmnm(object.getString("USRL_lmnm"));
                        itemUserDate.setUSRL_alias(object.getString("USRL_alias"));
                        itemUserDate.setCATG_cat_id_main(object.getString("CATG_cat_id_main"));
                        itemUserDate.setUSRL_number1_artw(object.getString("USRL_number1_artw"));
                        itemUserDate.setCATG_cat_id_second(object.getString("CATG_cat_id_second"));
                        itemUserDate.setUSRL_number2_artw(object.getString("USRL_number2_artw"));
                        itemUserDate.setCATG_cat_id_third(object.getString("CATG_cat_id_third"));
                        itemUserDate.setUSRL_number3_artw(object.getString("USRL_number3_artw"));
                        itemUserDate.setUSRL_email(object.getString("USRL_email"));
                        itemUserDate.setUSRL_email_alt(object.getString("USRL_email_alt"));
                        itemUserDate.setUSRL_passw(object.getString("USRL_passw"));
                        itemUserDate.setUSRL_phnr(object.getString("USRL_phnr"));
                        itemUserDate.setUSRL_dt_crea(object.getString("USRL_dt_crea"));
                        itemUserDate.setUSRL_dt_modf(object.getString("USRL_dt_modf"));
                        itemUserDate.setUSRL_img_url(object.getString("USRL_img_url"));
                        itemUserDate.setUSRL_ip_addr(object.getString("USRL_ip_addr"));
                        itemUserDate.setUSRL_lastaccess(object.getString("USRL_lastaccess"));
                        itemUserDate.setUSRL_addr1(object.getString("USRL_addr1"));
                        itemUserDate.setUSRL_addr2(object.getString("USRL_addr2"));
                        itemUserDate.setUSRL_addr3(object.getString("USRL_addr3"));
                        itemUserDate.setUSRL_town(object.getString("USRL_town"));
                        itemUserDate.setUSRL_state(object.getString("USRL_state"));
                        itemUserDate.setUSRL_country(object.getString("USRL_country"));
                        itemUserDate.setUSRL_zip(object.getString("USRL_zip"));
                        itemUserDate.setUSRL_facebook(object.getString("USRL_facebook"));
                        itemUserDate.setUSRL_twitter(object.getString("USRL_twitter"));
                        itemUserDate.setUSRL_instagram(object.getString("USRL_instagram"));
                        itemUserDate.setUSRL_youtube(object.getString("USRL_youtube"));
                        itemUserDate.setUSRL_social5(object.getString("USRL_social5"));
                        itemUserDate.setUSRL_social6(object.getString("USRL_social6"));
                        itemUserDate.setUSRL_social7(object.getString("USRL_social7"));
                        itemUserDate.setUSRL_qualif_in_avg(object.getString("USRL_qualif_in_avg"));
                        itemUserDate.setUSRL_qualif_in_nmbr(object.getString("USRL_qualif_in_nmbr"));
                        itemUserDate.setUSRL_qualif_out_avg(object.getString("USRL_qualif_out_avg"));
                        itemUserDate.setUSRL_qualif_out_nmbr(object.getString("USRL_qualif_out_nmbr"));
                        itemUserDate.setUSRL_s(object.getString("USRL_s"));
                        itemUserDate.setUSRL_c(object.getString("USRL_c"));
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
        return itemUserDate;
    }

    @Override
    protected void onPostExecute(ItemUserDate itemUserDate) {
        super.onPostExecute(itemUserDate);

        if (code == 200) {
            Log.d("", String.valueOf(code));
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_5));
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }
}
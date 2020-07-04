package com.creaarte.creaarte.WebService.Sets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.Models.ItemUserDate;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class SetComment extends AsyncTask<String, String, ItemUserDate> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private AppCreaarte appCreaarte;
    private int code = 500;
    private String error = "";

    public SetComment(Activity activity) {
        this.activity = activity;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ItemUserDate doInBackground(String... params) {

        ItemUserDate itemUserDate = new ItemUserDate();
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "setComment");
        client.addTextParam("comment", params[0]);
        client.addTextParam("id_article", params[1]);
        client.addTextParam("id_user", params[2]);
        client.addTextParam("ipAddress", params[3]);
        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONObject objectUser = new JSONObject(aux);
                        Log.d("json", objectUser.toString());
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
    protected void onPostExecute(ItemUserDate s) {
        super.onPostExecute(s);
        if (code == 200) {
            EditText editTextCommentArtsComments= activity.findViewById(R.id.editTextCommentArtsComments);
            editTextCommentArtsComments.setText("");
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast("");
        }
    }
}

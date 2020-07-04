package com.creaarte.creaarte.WebService.Update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;

public class SetUpdatePassword extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private AppCreaarte appCreaarte;
    private int code = 500;
    private String error = "";
    private String mensaje = "";
    private Dialog dialogChanger = null;

    public SetUpdatePassword(Activity activity) {
        this.activity = activity;
        appCreaarte = new AppCreaarte(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogChanger = new Dialog(activity, R.style.CustomDialogTheme);
        dialogChanger.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChanger.setCancelable(false);
        dialogChanger.setContentView(R.layout.dialog_charger);
        dialogChanger.show();
    }

    @Override
    protected String doInBackground(String... params) {
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "setUpdatePassword");
        client.addTextParam("id_user", params[0]);
        client.addTextParam("password", params[1]);
        client.addTextParam("confirm", params[2]);
        client.addTextParam("ipAddress", params[3]);
        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONObject objectUser = new JSONObject(aux);
                        Log.d("json", aux);
                        mensaje = objectUser.getString("Mensaje");
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
        return mensaje;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        if (code == 200) {
            showDialogRequest();
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_5));
        }

        if (dialogChanger.isShowing()) {
            dialogChanger.dismiss();
        }
    }

    private void showDialogRequest() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_welcome_);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView textViewTitleWelcome = dialog.findViewById(R.id.textViewTitleWelcome);
        textViewTitleWelcome.setText(R.string.text_variable_47);

        TextView textViewDescriptionWelcome = dialog.findViewById(R.id.textViewDescriptionWelcome);
        textViewDescriptionWelcome.setText(R.string.text_variable_46);

        Button buttonWelcome = dialog.findViewById(R.id.buttonWelcome);
        buttonWelcome.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}

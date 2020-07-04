package com.creaarte.creaarte.WebService.Sets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.Navigation;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.Models.ItemUserDate;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;

public class SetForgotPassword extends AsyncTask<String, String, ItemUserDate> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private AppCreaarte appCreaarte;
    private int code = 500;
    private String error = "";
    private Dialog dialogChanger = null;
    @SuppressLint("StaticFieldLeak")
    private TextInputEditText textInputEditTextEmailLoginRecoverAcount;

    public SetForgotPassword(Activity activity, TextInputEditText textInputEditTextEmailLoginRecoverAcount) {
        this.activity = activity;
        this.textInputEditTextEmailLoginRecoverAcount = textInputEditTextEmailLoginRecoverAcount;
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
    protected ItemUserDate doInBackground(String... params) {

        ItemUserDate itemUserDate = new ItemUserDate();
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "setForgotPassword");
        client.addTextParam("USRL_email", params[0]);
        client.addTextParam("USRL_ip_addr", params[1]);
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
            showDialogRequest();
            textInputEditTextEmailLoginRecoverAcount = activity.findViewById(R.id.textInputEditTextEmailLoginRecoverAcount);
            Navigation.findNavController(textInputEditTextEmailLoginRecoverAcount).navigate(R.id.action_nav_login_recover_password_fragment_to_nav_login_for_email_fragment);
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast(activity.getString(R.string.text_error_1));
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
        textViewTitleWelcome.setText(R.string.text_string_65);
        TextView textViewDescriptionWelcome = dialog.findViewById(R.id.textViewDescriptionWelcome);
        textViewDescriptionWelcome.setText(R.string.text_string_66);
        Button buttonWelcome = dialog.findViewById(R.id.buttonWelcome);
        buttonWelcome.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}

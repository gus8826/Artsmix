package com.creaarte.creaarte.WebService.Sets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Layouts.MenuMain.ContainerMenuMainActivity;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.Models.ItemUserDate;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.SQLHelper;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;

public class SetLoginFacebook extends AsyncTask<String, String, ItemUserDate> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private AppCreaarte appCreaarte;
    private int code = 500;
    private String error = "";
    private Dialog dialogChanger = null;

    public SetLoginFacebook(Activity activity) {
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
    protected ItemUserDate doInBackground(String... params) {
        ItemUserDate itemUserDate = new ItemUserDate();
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "setLoginFacebook");
        client.addTextParam("USRL_alias", params[0]);
        client.addTextParam("USRL_name", params[1]);
        client.addTextParam("USRL_lfnm", params[2]);
        client.addTextParam("USRL_lmnm", params[3]);
        client.addTextParam("USRL_img_url", params[4]);
        client.addTextParam("USRL_email", params[5]);
        client.addTextParam("USRL_facebook", params[6]);
        client.addTextParam("USRL_ip_addr", params[7]);
        client.addTextParam("USRT_id", params[8]);
        try {
            if (AppCreaarte.isOnlineNet()) {
                String aux = client.execute();
                code = client.getResponseCode();
                if (code == 200) {
                    try {
                        JSONObject objectUser = new JSONObject(aux);
                        Log.d("json", aux);
                        try {
                            itemUserDate.setUSRL_id(objectUser.getString("USRL_id"));
                            itemUserDate.setUSRL_alias(objectUser.getString("USRL_alias"));
                            itemUserDate.setUSRL_name(objectUser.getString("USRL_name"));
                            itemUserDate.setUSRL_email(objectUser.getString("USRL_email"));
                            itemUserDate.setUSRT_id(objectUser.getString("USRT_id"));
                            itemUserDate.setUSRL_img_url(objectUser.getString("USRL_img_url"));
                            SQLHelper dataSession = new SQLHelper(activity);
                            SQLiteDatabase db = dataSession.getWritableDatabase();

                            dataSession.getTableLoginUserInfo().AddInformationTableLoginUserInfo(db,
                                    itemUserDate.getUSRL_id(),
                                    itemUserDate.getUSRL_alias(),
                                    itemUserDate.getUSRL_name(),
                                    itemUserDate.getUSRL_email(),
                                    itemUserDate.getUSRT_id(),
                                    itemUserDate.getUSRL_img_url());

                        } catch (JSONException e) {
                            e.printStackTrace();
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
        return itemUserDate;
    }

    @Override
    protected void onPostExecute(ItemUserDate s) {
        super.onPostExecute(s);
        if (code == 200) {
            showDialogRequest();
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
        textViewTitleWelcome.setText(R.string.text_variable_47);

        TextView textViewDescriptionWelcome = dialog.findViewById(R.id.textViewDescriptionWelcome);
        textViewDescriptionWelcome.setText(R.string.text_variable_55);

        Button buttonWelcome = dialog.findViewById(R.id.buttonWelcome);
        buttonWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ContainerMenuMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
            dialog.dismiss();
        });
        dialog.show();
    }

}
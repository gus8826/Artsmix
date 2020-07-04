package com.creaarte.creaarte.WebService.Update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Layouts.MenuMain.ContainerMenuMainActivity;
import com.creaarte.creaarte.Models.ItemError;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.WebServiceClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SetUpdateImgUser extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private AppCreaarte appCreaarte;
    private int code = 500;
    private String error = "";
    private String mensaje = "";
    private Dialog dialogChanger = null;
    private File file;
    @SuppressLint("StaticFieldLeak")
    private ImageView imageViewUpdateProfileUser;

    public SetUpdateImgUser(Activity activity, ImageView imageViewUpdateProfileUser, File file) {
        this.activity = activity;
        this.file = file;
        this.imageViewUpdateProfileUser = imageViewUpdateProfileUser;
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
        WebServiceClient client = new WebServiceClient(WebServiceClient.RequestMethod.POST, "setUpdateImgUser");
        client.addTextParam("id_user", params[0]);
        client.addTextParam("ipAddress", params[1]);
        client.addFileParam("icon", file);

        //Log.d("id_user", "doInBackground: " + params[0]);
        //Log.d("ipAddress", "doInBackground: " + params[1]);
        //Log.d("icon", "doInBackground: " + file);

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
            imageViewUpdateProfileUser = activity.findViewById(R.id.imageViewUpdateProfileUser);
            Navigation.findNavController(imageViewUpdateProfileUser).navigate(R.id.action_nav_update_profile_user_fragment_to_nav_profile_user_fragment);

            showDialogRequest();
        } else if (code == 400) {
            appCreaarte.showToast(error);
        } else if (code == 500) {
            appCreaarte.showToast("");
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
        textViewDescriptionWelcome.setText(R.string.text_variable_49);

        Button buttonWelcome = dialog.findViewById(R.id.buttonWelcome);
        buttonWelcome.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}

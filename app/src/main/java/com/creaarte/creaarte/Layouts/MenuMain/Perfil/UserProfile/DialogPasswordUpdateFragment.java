package com.creaarte.creaarte.Layouts.MenuMain.Perfil.UserProfile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Update.SetUpdatePassword;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class DialogPasswordUpdateFragment extends DialogFragment implements View.OnClickListener {

    private TextInputEditText textInputEditTextPasswordUpdatePassword;
    private TextInputEditText textInputEditTextConfirmPasswordUpdatePassword;
    private Activity activity;
    private TableLoginUserInfo tableLoginUserInfo;
    private AppCreaarte appCreaarte;
    private String ipAddress = "";

    public DialogPasswordUpdateFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewDialogPasswordUpdateFragment = inflater.inflate(R.layout.fragment_dialog_password_update, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);

        assert activity != null;
        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }

        //TextView textViewTitlePasswordUpdate = viewDialogPasswordUpdateFragment.findViewById(R.id.textViewTitlePasswordUpdate);
        //TextView textViewUpdatePassword = viewDialogPasswordUpdateFragment.findViewById(R.id.textViewUpdatePassword);
        //TextInputLayout textInputLayoutPasswordUpdatePassword = viewDialogPasswordUpdateFragment.findViewById(R.id.textInputLayoutPasswordUpdatePassword);
        //TextInputLayout textInputLayoutConfirmPasswordUpdatePassword = viewDialogPasswordUpdateFragment.findViewById(R.id.textInputLayoutConfirmPasswordUpdatePassword);
        textInputEditTextPasswordUpdatePassword = viewDialogPasswordUpdateFragment.findViewById(R.id.textInputEditTextPasswordUpdatePassword);
        textInputEditTextConfirmPasswordUpdatePassword = viewDialogPasswordUpdateFragment.findViewById(R.id.textInputEditTextConfirmPasswordUpdatePassword);
        return viewDialogPasswordUpdateFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonClosedPasswordUpdate).setOnClickListener(this);
        view.findViewById(R.id.buttonUpdatePassword).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutPasswordUpdate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonClosedPasswordUpdate:
                dismiss();
                break;

            case R.id.buttonUpdatePassword:
                if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextPasswordUpdatePassword.getText()).toString())) {
                    textInputEditTextPasswordUpdatePassword.setError(getString(R.string.text_error_web_servicies_5));
                    textInputEditTextPasswordUpdatePassword.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextConfirmPasswordUpdatePassword.getText()).toString())) {
                    textInputEditTextConfirmPasswordUpdatePassword.setError(getString(R.string.text_error_web_servicies_5));
                    textInputEditTextConfirmPasswordUpdatePassword.requestFocus();
                } else if (textInputEditTextPasswordUpdatePassword.getText().toString().equals(textInputEditTextConfirmPasswordUpdatePassword.getText().toString())) {
                    showAlertDialog();
                } else {
                    textInputEditTextPasswordUpdatePassword.setError(getString(R.string.text_error_web_servicies_18));
                    textInputEditTextPasswordUpdatePassword.requestFocus();
                    textInputEditTextConfirmPasswordUpdatePassword.setError(getString(R.string.text_error_web_servicies_18));
                    textInputEditTextConfirmPasswordUpdatePassword.requestFocus();
                }
                break;

            default:
                break;
        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(getString(R.string.alert_dialog_update_password));
        dialog.setMessage(getString(R.string.text_variable_43));
        dialog.setCancelable(false);
        dialog.setPositiveButton(Html.fromHtml("<font color='#000000'>" + getString(R.string.text_variable_44) + "</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (appCreaarte.isNetDisponible()) {
                    if (AppCreaarte.isOnlineNet()) {
                        if (AppCreaarte.isConnectedWifi(activity)) {
                            ipAddress = appCreaarte.getDeviceWifiData();
                        } else if (AppCreaarte.isConnectedMobile(activity)) {
                            ipAddress = appCreaarte.getDeviceipMobileData();
                        }
                        new SetUpdatePassword(activity).execute(tableLoginUserInfo.getUSRL_id(), Objects.requireNonNull(textInputEditTextPasswordUpdatePassword.getText()).toString(), Objects.requireNonNull(textInputEditTextConfirmPasswordUpdatePassword.getText()).toString(), ipAddress);
                        dismiss();
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_5));
                    }
                } else {
                    appCreaarte.showToast(getString(R.string.text_error_5));
                }
            }
        });
        dialog.setNegativeButton(Html.fromHtml("<font color='#000000'>" + getString(R.string.text_variable_45) + "</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        dialog.show();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);
        super.onResume();
    }
}

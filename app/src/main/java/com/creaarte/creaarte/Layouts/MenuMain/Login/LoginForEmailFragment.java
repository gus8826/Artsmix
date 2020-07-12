package com.creaarte.creaarte.Layouts.MenuMain.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.Sets.SetLogin;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginForEmailFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private AppCreaarte appCreaarte;
    //private ImageView imageViewLogoLoginForEmail;
    //private TextView textViewTextLoginForEmail1;
    //private TextView textViewTextLoginForEmail2;
    //private TextView textViewTextLoginForEmail3;
    //private TextInputLayout textInputLayoutEmailLoginForEmail;
    //private TextInputLayout textInputLayoutPasswordLoginForEmail;
    private TextInputEditText textInputEditTextEmailLoginForEmail;
    private TextInputEditText textInputEditTextPasswordLoginForEmail;
    //private ScrollView scrollViewLoginForEmail;
    //private FrameLayout frameLayoutLoginForEmail;
    //private LinearLayout linearLayoutScrollLoginForEmail;
    //private ConstraintLayout constrainLayoutOptionsLoginForEmail;
    //private LinearLayout linearLayoutOptionsLoginForEmail;
    //private LinearLayout linearLayoutSpaceLoginForEmail_1;
    //private LinearLayout linearLayoutSpaceLoginForEmail_2;
    //private Button buttonClosedLoginForEmail;
    //private Button buttonRecoverPasswordLoginForEmail;
    //private Button buttonLoginForEmail;
    private String ipAddress = "";
    //private static final String TAG = "LoginForEmailFragment";

    public LoginForEmailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewLoginForEmailFragment = inflater.inflate(R.layout.fragment_login_for_email, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);

        assert activity != null;
        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }

        textInputEditTextEmailLoginForEmail = viewLoginForEmailFragment.findViewById(R.id.textInputEditTextEmailLoginForEmail);
        textInputEditTextPasswordLoginForEmail = viewLoginForEmailFragment.findViewById(R.id.textInputEditTextPasswordLoginForEmail);
        return viewLoginForEmailFragment;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonRecoverPasswordLoginForEmail).setOnClickListener(this);
        view.findViewById(R.id.buttonLoginForEmail).setOnClickListener(this);
        view.findViewById(R.id.buttonCreateNewAccountLoginForEmail).setOnClickListener(this);
        view.findViewById(R.id.frameLayoutLoginForEmail).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutScrollLoginForEmail).setOnClickListener(this);
        //view.findViewById(R.id.buttonClosedLoginForEmail).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

           /* case R.id.buttonClosedLoginForEmail:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_for_email_fragment_to_nav_login_options_fragment);
                break;*/

            case R.id.linearLayoutScrollLoginForEmail:
            case R.id.frameLayoutLoginForEmail:
                appCreaarte.hideTheKeyboard(activity, textInputEditTextEmailLoginForEmail);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextPasswordLoginForEmail);
                break;

            case R.id.buttonRecoverPasswordLoginForEmail:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_for_email_fragment_to_nav_login_recover_password_fragment);
                break;

            case R.id.buttonLoginForEmail:
                if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextEmailLoginForEmail.getText()).toString().trim())) {
                    textInputEditTextEmailLoginForEmail.setError(getString(R.string.text_error_3));
                    textInputEditTextEmailLoginForEmail.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextPasswordLoginForEmail.getText()).toString().trim())) {
                    textInputEditTextPasswordLoginForEmail.setError(getString(R.string.text_error_web_servicies_5));
                    textInputEditTextPasswordLoginForEmail.requestFocus();
                } else {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            new SetLogin(activity).execute(textInputEditTextEmailLoginForEmail.getText().toString(), textInputEditTextPasswordLoginForEmail.getText().toString(), ipAddress);
                        } else {
                            appCreaarte.showToast(getString(R.string.text_error_1));
                        }
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_1));
                    }
                }
                break;

            case R.id.buttonCreateNewAccountLoginForEmail:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_for_email_fragment_to_nav_login_create_new_account_fragment);
                break;

            default:
                break;
        }
    }
}

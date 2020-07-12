package com.creaarte.creaarte.Layouts.MenuMain.Login;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.Sets.SetForgotPassword;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class LoginRecoverAcountFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    //private FrameLayout frameLayoutLoginRecoverAcount;
    //private LinearLayout linearLayoutScrollLoginRecoverAcount;
    //private ScrollView scrollLoginRecoverAcount;
    //private TextInputLayout textInputLayoutEmailLoginRecoverAcount;
    private TextInputEditText textInputEditTextEmailLoginRecoverAcount;
    private AppCreaarte appCreaarte;
    //private static final String TAG = "LoginRecoverAcountFragment";
    private String ipAddress = "";

    public LoginRecoverAcountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewLoginRecoverPasswordFragment = inflater.inflate(R.layout.fragment_login_recover_acount, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);

        assert activity != null;
        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }
        //ImageView imageViewLogoLoginRecoverAcount = viewLoginRecoverPasswordFragment.findViewById(R.id.imageViewLogoLoginRecoverAcount);
        //TextView textViewLoginTextRecoverAcount1 = viewLoginRecoverPasswordFragment.findViewById(R.id.textViewLoginTextRecoverAcount1);
        //TextView textViewLoginTextRecoverAcount2 = viewLoginRecoverPasswordFragment.findViewById(R.id.textViewLoginTextRecoverAcount2);
        textInputEditTextEmailLoginRecoverAcount = viewLoginRecoverPasswordFragment.findViewById(R.id.textInputEditTextEmailLoginRecoverAcount);

        return viewLoginRecoverPasswordFragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.buttonClosedLoginRecoverAcount).setOnClickListener(this);
        view.findViewById(R.id.buttonRecoverPasswordLoginRecoverAcount).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutScrollLoginRecoverAcount).setOnClickListener(this);
        view.findViewById(R.id.frameLayoutLoginRecoverAcount).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*case R.id.buttonClosedLoginRecoverAcount:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_recover_password_fragment_to_nav_login_for_email_fragment);
                break;*/

            case R.id.buttonRecoverPasswordLoginRecoverAcount:
                if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextEmailLoginRecoverAcount.getText()).toString().trim())) {
                    textInputEditTextEmailLoginRecoverAcount.setError(getString(R.string.text_error_web_servicies_4));
                    textInputEditTextEmailLoginRecoverAcount.requestFocus();
                } else if (!appCreaarte.validateEmail(textInputEditTextEmailLoginRecoverAcount.getText().toString().trim())) {
                    textInputEditTextEmailLoginRecoverAcount.setError(getString(R.string.text_error_web_servicies_3));
                    textInputEditTextEmailLoginRecoverAcount.requestFocus();
                } else {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            new SetForgotPassword(activity, textInputEditTextEmailLoginRecoverAcount).execute(textInputEditTextEmailLoginRecoverAcount.getText().toString().trim(), ipAddress);
                        } else {
                            appCreaarte.showToast(getString(R.string.text_error_1));
                        }
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_1));
                    }
                }
                break;

            case R.id.frameLayoutLoginRecoverAcount:
            case R.id.linearLayoutScrollLoginRecoverAcount:
                appCreaarte.hideTheKeyboard(activity, textInputEditTextEmailLoginRecoverAcount);
                break;

            default:
                break;

        }
    }
}


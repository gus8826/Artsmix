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
import com.creaarte.creaarte.WebService.Sets.SetCreateUser;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class LoginCreateNewAccountFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private AppCreaarte appCreaarte;
    private String ipAddress = "";
    //private String idTypeUser = "3";
    //private FrameLayout frameLayoutLoginCreateNewAccount;
    //private LinearLayout linearLayoutLoginCreateNewAccount;
    //private ImageView imageViewLogoLoginCreateNewAccount;
    //private TextView textViewTextLoginCreateNewAccount1;
    //private TextView textViewTextLoginCreateNewAccount2;
    //private Button buttonClosedLoginCreateNewAccount;
    //private Button buttonLoginCreateNewAccount;
    //private ScrollView scrollViewLoginCreateNewAccount;
    //private TextInputLayout textInputLayoutNickNameLoginCreateNewAccount;
    //private TextInputLayout textInputLayoutPhoneNumberLoginCreateNewAccount;
    //private TextInputLayout textInputLayoutEmailLoginCreateNewAccount;
    //private TextInputLayout textInputLayoutPasswordLoginCreateNewAccount;
    //private TextInputLayout textInputLayoutConfirmPasswordLoginCreateNewAccount;
    private TextInputEditText textInputEditTextNickNameLoginCreateNewAccount;
    private TextInputEditText textInputEditTextPhoneNumberLoginCreateNewAccount;
    private TextInputEditText textInputEditTextEmailLoginCreateNewAccount;
    private TextInputEditText textInputEditTextPasswordLoginCreateNewAccount;
    private TextInputEditText textInputEditTextConfirmPasswordLoginCreateNewAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewLoginCreateNewAccountFragment = inflater.inflate(R.layout.fragment_login_create_new_account, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);

        assert activity != null;
        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }

        textInputEditTextNickNameLoginCreateNewAccount = viewLoginCreateNewAccountFragment.findViewById(R.id.textInputEditTextNickNameLoginCreateNewAccount);
        textInputEditTextPhoneNumberLoginCreateNewAccount = viewLoginCreateNewAccountFragment.findViewById(R.id.textInputEditTextPhoneNumberLoginCreateNewAccount);
        textInputEditTextEmailLoginCreateNewAccount = viewLoginCreateNewAccountFragment.findViewById(R.id.textInputEditTextEmailLoginCreateNewAccount);
        textInputEditTextPasswordLoginCreateNewAccount = viewLoginCreateNewAccountFragment.findViewById(R.id.textInputEditTextPasswordLoginCreateNewAccount);
        textInputEditTextConfirmPasswordLoginCreateNewAccount = viewLoginCreateNewAccountFragment.findViewById(R.id.textInputEditTextConfirmPasswordLoginCreateNewAccount);
        return viewLoginCreateNewAccountFragment;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.frameLayoutLoginCreateNewAccount).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutLoginCreateNewAccount).setOnClickListener(this);
        view.findViewById(R.id.buttonClosedLoginCreateNewAccount).setOnClickListener(this);
        view.findViewById(R.id.buttonLoginCreateNewAccount).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frameLayoutLoginCreateNewAccount:
            case R.id.linearLayoutLoginCreateNewAccount:
                appCreaarte.hideTheKeyboard(activity, textInputEditTextNickNameLoginCreateNewAccount);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextPhoneNumberLoginCreateNewAccount);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextEmailLoginCreateNewAccount);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextPasswordLoginCreateNewAccount);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextConfirmPasswordLoginCreateNewAccount);
                break;

            case R.id.buttonClosedLoginCreateNewAccount:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_create_new_account_fragment_to_nav_login_for_email_fragment);
                break;

            case R.id.buttonLoginCreateNewAccount:
                if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextNickNameLoginCreateNewAccount.getText()).toString())) {
                    textInputEditTextNickNameLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_8));
                    textInputEditTextNickNameLoginCreateNewAccount.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextEmailLoginCreateNewAccount.getText()).toString())) {
                    textInputEditTextEmailLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_4));
                    textInputEditTextEmailLoginCreateNewAccount.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextPasswordLoginCreateNewAccount.getText()).toString())) {
                    textInputEditTextPasswordLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_5));
                    textInputEditTextPasswordLoginCreateNewAccount.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextConfirmPasswordLoginCreateNewAccount.getText()).toString())) {
                    textInputEditTextConfirmPasswordLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_5));
                    textInputEditTextConfirmPasswordLoginCreateNewAccount.requestFocus();
                } else if (!appCreaarte.validateEmail(textInputEditTextEmailLoginCreateNewAccount.getText().toString().trim())) {
                    textInputEditTextEmailLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_3));
                    textInputEditTextEmailLoginCreateNewAccount.requestFocus();
                } else if (textInputEditTextPasswordLoginCreateNewAccount.getText().toString().equals(textInputEditTextConfirmPasswordLoginCreateNewAccount.getText().toString())) {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            new SetCreateUser(activity).execute(
                                    textInputEditTextNickNameLoginCreateNewAccount.getText().toString(),
                                    Objects.requireNonNull(textInputEditTextPhoneNumberLoginCreateNewAccount.getText()).toString(),
                                    textInputEditTextEmailLoginCreateNewAccount.getText().toString(),
                                    ipAddress,
                                    textInputEditTextPasswordLoginCreateNewAccount.getText().toString(),
                                    textInputEditTextPasswordLoginCreateNewAccount.getText().toString(),
                                    "3"
                            );
                        } else {
                            appCreaarte.showToast(getString(R.string.text_error_1));
                        }
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_1));
                    }
                } else {
                    textInputEditTextPasswordLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_18));
                    textInputEditTextPasswordLoginCreateNewAccount.requestFocus();
                    textInputEditTextConfirmPasswordLoginCreateNewAccount.setError(getString(R.string.text_error_web_servicies_18));
                    textInputEditTextConfirmPasswordLoginCreateNewAccount.requestFocus();
                }
                break;

            default:
                break;
        }
    }
}

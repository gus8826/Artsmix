package com.creaarte.creaarte.Layouts.MenuMain.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.WebService.Sets.SetLoginFacebook;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginOptionsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentLoginOptions";
    private Activity activity;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private AppCreaarte appCreaarte;
    private String username = "";
    private String name = "";
    private String lastMName = "";
    private String lastFName = "";
    private String url = "";
    private String email = "";
    private String idFacebook = "";
    private String ipAddress = "";
    private LoginButton loginButtonFacebook;
    private CallbackManager callbackManagerFacebook;

    public LoginOptionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewLoginOptionsFragment = inflater.inflate(R.layout.fragment_login_options, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);

        assert activity != null;
        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }

        FacebookSdk.sdkInitialize(activity);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManagerFacebook = CallbackManager.Factory.create();
        loginButtonFacebook = viewLoginOptionsFragment.findViewById(R.id.loginButtonFacebook);
        loginButtonFacebook.setReadPermissions("email", "public_profile");
        loginButtonFacebook.setFragment(this);
        loginButtonFacebook.registerCallback(callbackManagerFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        firebaseAuthStateListener = firebaseAuth -> {
            FirebaseUser userFacebook = firebaseAuth.getCurrentUser();
            if (userFacebook != null) {
                Log.d(TAG, "onAuthStateChanged: " + userFacebook.getDisplayName());
            }
        };
        //Log.d(TAG, "ip"+ appCreaarte.getDeviceWifiData() );
        //Log.d(TAG,"ip"+  appCreaarte.getDeviceipMobileData());
        //appCreaarte.getDeviceWifiData();
        //appCreaarte.getDeviceipMobileData();
        //AllPrermission();
        return viewLoginOptionsFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.buttonClosedLoginOptions).setOnClickListener(this);
        view.findViewById(R.id.buttonFacebookLoginOptions).setOnClickListener(this);
        view.findViewById(R.id.buttonGoogleLoginOptions).setOnClickListener(this);
        view.findViewById(R.id.buttonEmailLoginOptions).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*case R.id.buttonClosedLoginOptions:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_options_fragment_to_nav_menu_main);
                break;*/

            case R.id.buttonFacebookLoginOptions:
                loginButtonFacebook.performClick();
                break;

            case R.id.buttonGoogleLoginOptions:
                //controllerTransactionFunctionsClass.goLoginUserScreen();
                break;

            case R.id.buttonEmailLoginOptions:
                Navigation.findNavController(v).navigate(R.id.action_nav_login_options_fragment_to_nav_login_for_email_fragment);
                break;

            default:
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookToken(final AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity, task -> {
            if (task.isComplete()) {

                Bundle bundle = new Bundle();
                bundle.putString("fields", "id, email, picture.type(large)");
                new GraphRequest(token, "me", bundle, HttpMethod.GET, response -> {
                    JSONObject data = response.getJSONObject();
                    try {
                        url = data.getJSONObject("picture").getJSONObject("data").getString("url");
                        //picture.replace("&","--");
                        Log.d("pictureFacebook", url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }).executeAsync();

                GraphRequest request = GraphRequest.newMeRequest(token, (object, response) -> {

                    try {
                        name = object.getString("name");
                        Log.d("name", name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        username = object.getString("first_name");
                        Log.d("username", username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        lastFName = object.getString("last_name");
                        Log.d("last_name", lastFName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        lastMName = object.getString("middle_name");
                        Log.d("last_name", lastMName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        email = object.getString("email");
                        Log.d("email", email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        idFacebook = object.getString("id");
                        Log.d("idFacebook", idFacebook);

                        if (AppCreaarte.isConnectedWifi(activity)) {
                            ipAddress = appCreaarte.getDeviceWifiData();
                        } else if (AppCreaarte.isConnectedMobile(activity)) {
                            ipAddress = appCreaarte.getDeviceipMobileData();
                        }

                        new SetLoginFacebook(activity).execute(username, name, lastFName, lastMName, url, email, idFacebook, ipAddress, "3");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("field", "username, name, lastFName, lastMName, url, emailidFacebook");
                request.setParameters(parameters);
                request.executeAsync();
            } else {
                appCreaarte.showToast(activity.getString(R.string.text_error_6));
            }
        });

    }
}

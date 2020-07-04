package com.creaarte.creaarte.Layouts.MenuMain.Login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.creaarte.creaarte.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONObject;
import java.util.Objects;

public class LoginOptionsDialog extends DialogFragment implements View.OnClickListener{

    private Activity activity;
    private View viewLoginOptionsDialog;
    private LoginButton loginButtonFacebook;
    private CallbackManager callbackManagerFacebook;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private static final String TAG = "LoginOptionsDialog";

    public LoginOptionsDialog (){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLoginOptionsDialog = inflater.inflate(R.layout.dialog_login_options, container);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ImageView imageViewLogoLoginOptions = viewLoginOptionsDialog.findViewById(R.id.imageViewLogoLoginOptions);
        TextView textViewTitleLoginOptions = viewLoginOptionsDialog.findViewById(R.id.textViewTitleLoginOptions);
        TextView textViewSubTitleLoginOptions = viewLoginOptionsDialog.findViewById(R.id.textViewSubTitleLoginOptions);
        TextView textViewSubTitleLoginOptions2 = viewLoginOptionsDialog.findViewById(R.id.textViewSubTitleLoginOptions2);
        TextView textViewOptionLoginOptions = viewLoginOptionsDialog.findViewById(R.id.textViewOptionLoginOptions);

        activity = getActivity();

        FacebookSdk.sdkInitialize(activity);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManagerFacebook = CallbackManager.Factory.create();
        loginButtonFacebook = viewLoginOptionsDialog.findViewById(R.id.loginButtonFacebook);
        loginButtonFacebook.setReadPermissions("public_profile", "email", "user_birthday");
        loginButtonFacebook.setFragment(this);
        //
        loginButtonFacebook.registerCallback(callbackManagerFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onError: error");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: error");
            }
        });

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser userFacebook = firebaseAuth.getCurrentUser();
                if (userFacebook != null) {
                    Log.d(TAG, "onAuthStateChanged: " + userFacebook.getDisplayName());
                }
            }
        };


        return viewLoginOptionsDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonClosedDialogLoginOptions).setOnClickListener(this);
        view.findViewById(R.id.buttonFacebookLoginOptions).setOnClickListener(this);
        view.findViewById(R.id.buttonGoogleLoginOptions).setOnClickListener(this);
        view.findViewById(R.id.buttonEmailLoginOptions).setOnClickListener(this);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonClosedDialogLoginOptions:
                dismiss();
                break;
            case R.id.buttonFacebookLoginOptions:
                loginButtonFacebook.performClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //firebaseAuthWithGoogle.addAuthStateListener(firebaseAuthListenerWithGoogle);
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //if (firebaseAuthWithGoogle != null) {
        //finalizar el hilo de Google
        //firebaseAuthWithGoogle.removeAuthStateListener(firebaseAuthListenerWithGoogle);
        //finalizar el hilo de facebook manager
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
        //}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void handleFacebookToken(final AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    //Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Log.i(TAG, "onComplete: login completed with user: " + user.getDisplayName());
                    GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            /*try {
                                AppTonight.email = object.getString("email");
                                //Log.d("email", AppTonight.email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                AppTonight.username = object.getString("name");
                                //Log.d("username", username);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                AppTonight.last_name = object.getString("last_name");
                                //Log.d("flag", last_name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                AppTonight.id = object.getString("id");
                                //Log.d("idFacebook", id);
                                AppTonight.picture = "http://graph.facebook.com/" + AppTonight.id + "/picture?type=large";
                                //new GetLogin(activity, viewFragmentLoginOptions).execute(AppTonight.email, AppTonight.password, "2", AppTonight.id, AppTonight.username, AppTonight.picture, AppTonight.idCatCountry, AppTonight.idCatTypeUser);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("field", "email, password, \"2\", id, username, picture");
                    request.setParameters(parameters);
                    request.executeAsync();

                    //appStatus.showToast("inicio session");

                } else {
                    ///appStatus.showToast("no inicio session");
                }
            }
        });

    }
}

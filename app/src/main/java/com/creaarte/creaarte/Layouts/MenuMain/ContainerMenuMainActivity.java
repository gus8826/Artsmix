package com.creaarte.creaarte.Layouts.MenuMain;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Controllers.CircleTransform;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ContainerMenuMainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private AppCreaarte appCreaarte;
    private TableLoginUserInfo tableLoginUserInfo;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main_container);

        appCreaarte = new AppCreaarte(this);
        tableLoginUserInfo = new TableLoginUserInfo(this);

        fm = getSupportFragmentManager();
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_menu_main, R.id.nav_home, R.id.nav_slideshow, R.id.nav_login_options_fragment, R.id.nav_profile_user_fragment).setDrawerLayout(drawer).build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_menu_main_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_login_options_fragment) {
                    toolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.nav_login_for_email_fragment) {
                    toolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.nav_login_create_new_account_fragment) {
                    toolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.nav_login_recover_password_fragment) {
                    toolbar.setVisibility(View.GONE);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });

        View mParent = navigationView.getHeaderView(0);

        ImageView imageViewProfileUserMainMenu = mParent.findViewById(R.id.imageViewProfileUserMainMenu);
        ImageView imageViewLogoMainMenu = mParent.findViewById(R.id.imageViewLogoMainMenu);
        TextView textViewUserNameMainMenu = mParent.findViewById(R.id.textViewUserNameMainMenu);
        TextView textViewEmailUserMenuMain = mParent.findViewById(R.id.textViewEmailUserMenuMain);

        ImageView imageViewLogoutUserMenuMain = mParent.findViewById(R.id.imageViewLogoutUserMenuMain);
        imageViewLogoutUserMenuMain.setOnClickListener(this);

        if (!tableLoginUserInfo.getUSRL_id().isEmpty()) {
            navigationView.getMenu().findItem(R.id.nav_login_options_fragment).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile_user_fragment).setVisible(true);
            textViewUserNameMainMenu.setText(tableLoginUserInfo.getUSRL_name());
            textViewEmailUserMenuMain.setText(tableLoginUserInfo.getUSRL_email());
            imageViewProfileUserMainMenu.setVisibility(View.VISIBLE);
            imageViewLogoMainMenu.setVisibility(View.GONE);
            imageViewLogoutUserMenuMain.setVisibility(View.VISIBLE);
            Picasso.get().load(tableLoginUserInfo.getUSRL_img_url().replace("..", AppCreaarte.BASE_IMAGE_URL)).transform(new CircleTransform()).resize(150, 150).into(imageViewProfileUserMainMenu);
        } else {
            navigationView.getMenu().findItem(R.id.nav_login_options_fragment).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile_user_fragment).setVisible(false);
            imageViewProfileUserMainMenu.setVisibility(View.GONE);
            imageViewLogoMainMenu.setVisibility(View.VISIBLE);
            textViewUserNameMainMenu.setVisibility(View.GONE);
            textViewEmailUserMenuMain.setVisibility(View.GONE);
            imageViewLogoutUserMenuMain.setVisibility(View.GONE);
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.creaarte.creaarte", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                //et.setText("" + something);
                Log.e("hashkey", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*@Override
    /Users/gustavo/Desktop/crearteapk
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_menu_main_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_menu_main_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewLogoutUserMenuMain:
                isLogout();
                break;

            default:
                break;
        }
    }

    private void isLogout() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.alert_dialog_update_password));
        dialog.setMessage(getString(R.string.text_variable_58));
        dialog.setCancelable(true);
        dialog.setPositiveButton(Html.fromHtml("<font color='#000000'>" + getString(R.string.text_variable_44) + "</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tableLoginUserInfo.deleteSesionUser();
                appCreaarte.showToast(getString(R.string.text_variable_59));
                recreate();
            }
        });

        dialog.setNegativeButton(Html.fromHtml("<font color='#000000'>" + getString(R.string.text_variable_45) + "</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        dialog.show();
    }

}

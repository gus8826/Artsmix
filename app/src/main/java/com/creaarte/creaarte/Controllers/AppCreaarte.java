package com.creaarte.creaarte.Controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.creaarte.creaarte.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppCreaarte {

    private Activity activity;
    //public static final String BASE_URL = "http://www.creaarte.mx/webServices/";
    public static final String BASE_URL = "http://www.artz-atoz.info/webServices/";
    public static final String BASE_IMAGE_URL = "http://www.artz-atoz.info";

    public String[] arrayStringGender = {"Mujer", "Hombre", "otro"};

    public AppCreaarte(Activity activity) {
        this.activity = activity;
    }

    public boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isConnectedMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public String getDeviceWifiData() {
        WifiManager wifiMgr = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = Objects.requireNonNull(wifiMgr).getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    public String getDeviceipMobileData() {
        try {
            for (java.util.Enumeration<java.net.NetworkInterface> en = java.net.NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                java.net.NetworkInterface networkinterface = en.nextElement();
                for (java.util.Enumeration<java.net.InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    java.net.InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public static Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.artz-atoz.info");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public void hideTheKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public boolean validateNullEdtTxt(String string) {
        if (string != null && string.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void showToast(String mnjs) {
        Toast.makeText(activity, mnjs, Toast.LENGTH_SHORT).show();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    public String UUID() {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static boolean saveImage(Bitmap signature, String kingDir, String nameFile) {

        boolean verify = false;
        String root = Environment.getExternalStorageDirectory().toString();
        // the directory where the signature will be saved
        File myDir = new File(root + "/" + kingDir);

        // make the directory if it does not exist yet
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        // set the file name of your choice
        String fname = nameFile + ".png";
        // in our case, we delete the previous file, you can remove this
        File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            // save the signature
            int outWidth;
            int outHeight;
            int inWidth = signature.getWidth() / 3;
            int inHeight = signature.getHeight() / 3;
            int finalWidth = inWidth * 2;
            int finalHeight = inHeight * 2;

            outWidth = finalWidth;
            outHeight = finalHeight;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(signature, outWidth, outHeight, false);
            FileOutputStream out = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 70, out);
            out.flush();
            out.close();
            verify = true;

        } catch (Exception e) {
            e.printStackTrace();
            verify = false;
        }
        return verify;
    }

    public static boolean sizePhoto(String nameFile) {

        boolean verify = false;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Cotizalo/" + nameFile + ".jpg");

        try {

            Bitmap signature = BitmapFactory.decodeFile(root + "/Cotizalo/" + nameFile + ".jpg");
            int outWidth;
            int outHeight;

            int inWidth = 0;
            int inHeight = 0;
            int finalWidth;
            int finalHeight;

            if (signature.getWidth() > 1280 || signature.getHeight() > 1280) {

                inWidth = signature.getWidth() / 3;
                inHeight = signature.getHeight() / 3;
                finalWidth = inWidth * 2;
                finalHeight = inHeight * 2;

            } else {
                finalWidth = inWidth;
                finalHeight = inHeight;
            }

            outWidth = finalWidth;
            outHeight = finalHeight;

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(signature, outWidth, outHeight, false);
            FileOutputStream out = new FileOutputStream(myDir);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
            verify = true;

        } catch (Exception e) {
            e.printStackTrace();
            verify = false;
        }
        return verify;
    }

    public static boolean checkImage(String kingDir, String nameFile) {
        String root = Environment.getExternalStorageDirectory().toString();

        try {
            File f = new File(root + "/" + kingDir + "/" + nameFile);
            if (!f.exists()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

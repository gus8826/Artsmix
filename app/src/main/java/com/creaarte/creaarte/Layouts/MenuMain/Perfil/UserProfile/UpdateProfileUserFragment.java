package com.creaarte.creaarte.Layouts.MenuMain.Perfil.UserProfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Controllers.CircleTransform;
import com.creaarte.creaarte.Models.ItemGender;
import com.creaarte.creaarte.Models.ItemUserDate;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Gets.GetGendNotPag;
import com.creaarte.creaarte.WebService.Update.SetUpdateImgUser;
import com.creaarte.creaarte.WebService.Update.SetUpdateUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UpdateProfileUserFragment extends Fragment implements View.OnClickListener {

    private FrameLayout frameLayoutUpdateProfileUser;
    private ScrollView scrollViewUpdateProfileUser;
    private LinearLayout linearLayoutUpdateProfileUser;
    private ImageView imageViewUpdateProfileUser;
    private TextInputLayout textInputLayoutNickNameUpdateProfileUser;
    private TextInputLayout textInputLayoutNameUpdateProfileUser;
    private TextInputLayout textInputLayoutPaternalSurnameUpdateProfileUser;
    private TextInputLayout textInputLayoutMaternalSurnameUpdateProfileUser;
    private TextInputLayout textInputLayoutPhoneNumberUpdateProfileUser;
    private TextInputLayout textInputLayoutEmailUpdateProfileUser;
    private TextInputLayout textInputLayoutHomeAddressUpdateProfileUser_1;
    private TextInputLayout textInputLayoutHomeAddressUpdateProfileUser_2;
    private TextInputLayout textInputLayoutHomeAddressUpdateProfileUser_3;
    private TextInputEditText textInputEditTextNickNameUpdateProfileUser;
    private TextInputEditText textInputEditTextNameUpdateProfileUser;
    private TextInputEditText textInputEditTextPaternalSurnameUpdateProfileUser;
    private TextInputEditText textInputEditTextMaternalSurnameUpdateProfileUser;
    private TextInputEditText textInputEditTextPhoneNumberUpdateProfileUser;
    private TextInputEditText textInputEditTextEmailUpdateProfileUser;
    private TextInputEditText textInputEditTextHomeAddressUpdateProfileUser_1;
    private TextInputEditText textInputEditTextHomeAddressUpdateProfileUser_2;
    private TextInputEditText textInputEditTextHomeAddressUpdateProfileUser_3;
    private Spinner spinnerGenderUpdateProfileUser;
    private Activity activity;
    private FragmentManager fm;
    private TableLoginUserInfo tableLoginUserInfo;
    private AppCreaarte appCreaarte;
    private String ipAddress = "";
    private Bundle bundle;
    private ItemUserDate itemUserDate;
    private String gender = "";
    private static final String TAG = "ProfileUserFragment";
    private static String APP_DIRECTORY = "Artmix/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Artmix";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewUpdateProfileUserFragment = inflater.inflate(R.layout.fragment_update_profile_user, container, false);
        activity = getActivity();
        assert activity != null;
        fm = getChildFragmentManager();//.getSupportFragmentManager();
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);
        itemUserDate = new ItemUserDate();

        if (AppCreaarte.isConnectedWifi(activity)) {
            ipAddress = appCreaarte.getDeviceWifiData();
        } else if (AppCreaarte.isConnectedMobile(activity)) {
            ipAddress = appCreaarte.getDeviceipMobileData();
        }

        textInputLayoutNickNameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutNickNameUpdateProfileUser);
        textInputEditTextNickNameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextNickNameUpdateProfileUser);

        textInputEditTextNameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextNameUpdateProfileUser);
        textInputLayoutNameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutNameUpdateProfileUser);

        textInputEditTextPaternalSurnameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextPaternalSurnameUpdateProfileUser);
        textInputLayoutPaternalSurnameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutPaternalSurnameUpdateProfileUser);

        textInputEditTextMaternalSurnameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextMaternalSurnameUpdateProfileUser);
        textInputLayoutMaternalSurnameUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutMaternalSurnameUpdateProfileUser);

        textInputEditTextPhoneNumberUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextPhoneNumberUpdateProfileUser);
        textInputLayoutPhoneNumberUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutPhoneNumberUpdateProfileUser);

        textInputEditTextEmailUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextEmailUpdateProfileUser);
        textInputLayoutEmailUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutEmailUpdateProfileUser);

        textInputEditTextHomeAddressUpdateProfileUser_1 = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextHomeAddressUpdateProfileUser_1);
        textInputLayoutHomeAddressUpdateProfileUser_1 = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutHomeAddressUpdateProfileUser_1);

        textInputEditTextHomeAddressUpdateProfileUser_2 = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextHomeAddressUpdateProfileUser_2);
        textInputLayoutHomeAddressUpdateProfileUser_2 = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutHomeAddressUpdateProfileUser_2);

        textInputEditTextHomeAddressUpdateProfileUser_3 = viewUpdateProfileUserFragment.findViewById(R.id.textInputEditTextHomeAddressUpdateProfileUser_3);
        textInputLayoutHomeAddressUpdateProfileUser_3 = viewUpdateProfileUserFragment.findViewById(R.id.textInputLayoutHomeAddressUpdateProfileUser_3);

        spinnerGenderUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.spinnerGenderUpdateProfileUser);
        imageViewUpdateProfileUser = viewUpdateProfileUserFragment.findViewById(R.id.imageViewUpdateProfileUser);

        reloadSpinner();

        bundle = getArguments();
        if (bundle != null) {
            itemUserDate = bundle.getParcelable("itemUserDate");
            reloadData(itemUserDate);
        }

        if (myRequestStoragePermission()) {
            Log.d(TAG, "onCreateView: " + TAG);
        }

        return viewUpdateProfileUserFragment;
    }

    private void reloadData(ItemUserDate itemUserDate) {

        if (!Objects.requireNonNull(itemUserDate).getUSRL_img_url().isEmpty()) {
            Picasso.get().load(itemUserDate.getUSRL_img_url().replace("..", AppCreaarte.BASE_IMAGE_URL)).transform(new CircleTransform()).resize(150, 150).into(imageViewUpdateProfileUser);
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_alias().isEmpty()) {
            textInputLayoutNickNameUpdateProfileUser.setHint(itemUserDate.getUSRL_alias());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_name().isEmpty()) {
            textInputLayoutNameUpdateProfileUser.setHint(itemUserDate.getUSRL_name());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_lfnm().isEmpty()) {
            textInputLayoutPaternalSurnameUpdateProfileUser.setHint(itemUserDate.getUSRL_lfnm());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_lmnm().isEmpty()) {
            textInputLayoutMaternalSurnameUpdateProfileUser.setHint(itemUserDate.getUSRL_lmnm());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_phnr().isEmpty()) {
            textInputLayoutPhoneNumberUpdateProfileUser.setHint(itemUserDate.getUSRL_phnr());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_email().isEmpty()) {
            textInputLayoutEmailUpdateProfileUser.setHint(itemUserDate.getUSRL_email());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_addr1().isEmpty()) {
            textInputLayoutHomeAddressUpdateProfileUser_1.setHint(itemUserDate.getUSRL_addr1());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_addr2().isEmpty()) {
            textInputLayoutHomeAddressUpdateProfileUser_2.setHint(itemUserDate.getUSRL_addr2());
        }

        if (!Objects.requireNonNull(itemUserDate).getUSRL_addr3().isEmpty()) {
            textInputLayoutHomeAddressUpdateProfileUser_3.setHint(itemUserDate.getUSRL_addr3());
        }
    }

    private void reloadSpinner() {
        if (appCreaarte.isNetDisponible()) {
            if (AppCreaarte.isOnlineNet()) {
                if (AppCreaarte.isConnectedWifi(activity)) {
                    ipAddress = appCreaarte.getDeviceWifiData();
                } else if (AppCreaarte.isConnectedMobile(activity)) {
                    ipAddress = appCreaarte.getDeviceipMobileData();
                }
                new GetGendNotPag(activity, spinnerGenderUpdateProfileUser).execute(itemUserDate.getUSRL_id(), ipAddress);
                spinnerGenderUpdateProfileUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemGender itemGender = (ItemGender) adapterView.getItemAtPosition(i);
                        gender = itemGender.getGEND_id();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else {
                appCreaarte.showToast(getString(R.string.text_error_1));
            }
        } else {
            appCreaarte.showToast(getString(R.string.text_error_1));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.frameLayoutUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.constraintLayoutNamesUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.constraintLayoutSurnamesUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.constraintLayoutTypeUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.constraintLayoutButtonsUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.buttonImageUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.buttonUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.buttonPasswordUpdateProfileUser).setOnClickListener(this);
        view.findViewById(R.id.imageViewUpdateProfileUser).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frameLayoutUpdateProfileUser:
            case R.id.linearLayoutUpdateProfileUser:
            case R.id.constraintLayoutNamesUpdateProfileUser:
            case R.id.constraintLayoutSurnamesUpdateProfileUser:
            case R.id.constraintLayoutTypeUpdateProfileUser:
            case R.id.constraintLayoutButtonsUpdateProfileUser:
                appCreaarte.hideTheKeyboard(activity, textInputEditTextNickNameUpdateProfileUser);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextNameUpdateProfileUser);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextPaternalSurnameUpdateProfileUser);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextMaternalSurnameUpdateProfileUser);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextPhoneNumberUpdateProfileUser);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextEmailUpdateProfileUser);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextHomeAddressUpdateProfileUser_1);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextHomeAddressUpdateProfileUser_2);
                appCreaarte.hideTheKeyboard(activity, textInputEditTextHomeAddressUpdateProfileUser_3);
                break;

            case R.id.imageViewUpdateProfileUser:
            case R.id.buttonImageUpdateProfileUser:
                showOptions();
                break;

            case R.id.buttonPasswordUpdateProfileUser:
                showEditDialog();
                break;

            case R.id.buttonUpdateProfileUser:
                if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextNickNameUpdateProfileUser.getText()).toString())) {
                    textInputEditTextNickNameUpdateProfileUser.setError(getString(R.string.text_error_web_servicies_8));
                    textInputEditTextNickNameUpdateProfileUser.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextNameUpdateProfileUser.getText()).toString())) {
                    textInputEditTextNameUpdateProfileUser.setError(getString(R.string.text_error_web_servicies_8));
                    textInputEditTextNameUpdateProfileUser.requestFocus();
                } else if (!appCreaarte.validateNullEdtTxt(Objects.requireNonNull(textInputEditTextEmailUpdateProfileUser.getText()).toString())) {
                    textInputEditTextEmailUpdateProfileUser.setError(getString(R.string.text_error_web_servicies_4));
                    textInputEditTextEmailUpdateProfileUser.requestFocus();
                } else if (!appCreaarte.validateEmail(textInputEditTextEmailUpdateProfileUser.getText().toString().trim())) {
                    textInputEditTextEmailUpdateProfileUser.setError(getString(R.string.text_error_web_servicies_3));
                    textInputEditTextEmailUpdateProfileUser.requestFocus();
                } else {
                    showAlertDialogUpdatye();
                }

                break;

            default:
                break;

        }
    }

    private void showAlertDialogUpdatye() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(activity.getString(R.string.text_string_68));
        dialog.setMessage(getString(R.string.text_string_69));
        dialog.setCancelable(false);
        dialog.setPositiveButton(getString(R.string.text_variable_44), (dialog1, id) -> {
            if (appCreaarte.isNetDisponible()) {
                if (AppCreaarte.isOnlineNet()) {
                    if (AppCreaarte.isConnectedWifi(activity)) {
                        ipAddress = appCreaarte.getDeviceWifiData();
                    } else if (AppCreaarte.isConnectedMobile(activity)) {
                        ipAddress = appCreaarte.getDeviceipMobileData();
                    }
                    new SetUpdateUser(activity, textInputEditTextHomeAddressUpdateProfileUser_3).execute(
                            tableLoginUserInfo.getUSRL_id(),
                            Objects.requireNonNull(textInputEditTextNickNameUpdateProfileUser.getText()).toString(),
                            Objects.requireNonNull(textInputEditTextNameUpdateProfileUser.getText()).toString(),
                            Objects.requireNonNull(textInputEditTextPaternalSurnameUpdateProfileUser.getText()).toString(),
                            Objects.requireNonNull(textInputEditTextMaternalSurnameUpdateProfileUser.getText()).toString(),
                            Objects.requireNonNull(textInputEditTextPhoneNumberUpdateProfileUser.getText()).toString(),
                            gender,
                            Objects.requireNonNull(textInputEditTextEmailUpdateProfileUser.getText()).toString(),
                            ipAddress,
                            Objects.requireNonNull(textInputEditTextHomeAddressUpdateProfileUser_1.getText()).toString(),
                            Objects.requireNonNull(textInputEditTextHomeAddressUpdateProfileUser_2.getText()).toString(),
                            Objects.requireNonNull(textInputEditTextHomeAddressUpdateProfileUser_3.getText()).toString()
                    );
                } else {
                    appCreaarte.showToast(getString(R.string.text_error_5));
                }
            } else {
                appCreaarte.showToast(getString(R.string.text_error_5));
            }
        });
        dialog.setNegativeButton(R.string.text_variable_45, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        dialog.show();
    }

    private void showEditDialog() {
        DialogPasswordUpdateFragment editNameDialogFragment = new DialogPasswordUpdateFragment();
        editNameDialogFragment.show(fm, TAG);
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galería", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.text_variable_54);
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Tomar foto") {
                    openCamera();
                } else if (option[which] == "Elegir de galería") {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                } else if (option[which] == "Eliminar") {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private boolean myRequestStoragePermission() {

        if ((activity.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (activity.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = file.mkdirs();
        }
        if (isDirectoryCreated) {

            //long timestamp = System.currentTimeMillis() / 1000;
            //String imageName = timestamp + ".jpg";
            String imageName = (System.currentTimeMillis() / 1000) + ".jpg";
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + imageName;
            File newFile = new File(mPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authorities = activity.getApplicationContext().getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(activity, authorities, newFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            }
            //int PHOTO_CODE = 200;
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
            }
        } else {
            showExplanation();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(activity, new String[]{mPath}, null, (path, uri) -> {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> Uri = " + uri);
                        file = new File(path);
                    });

                    Matrix matrix = new Matrix();
                    matrix.postRotate(90.0f);
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    try {
                        file = savebitmap(rotatedBitmap);
                        setUpdateUser(file);
                        imageViewUpdateProfileUser.setImageBitmap(rotatedBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case SELECT_PICTURE:
                    Uri miPath = data.getData();
                    file = new File(String.valueOf(miPath));
                    setUpdateUser(file);
                    imageViewUpdateProfileUser.setImageURI(miPath);
                    break;

            }
        }

    }

    private static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "testimage.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

    private void setUpdateUser(File fileUpdateImageProfileUser) {
        if (appCreaarte.isNetDisponible()) {
            if (AppCreaarte.isOnlineNet()) {
                if (AppCreaarte.isConnectedWifi(activity)) {
                    ipAddress = appCreaarte.getDeviceWifiData();
                } else if (AppCreaarte.isConnectedMobile(activity)) {
                    ipAddress = appCreaarte.getDeviceipMobileData();
                }
                new SetUpdateImgUser(activity, imageViewUpdateProfileUser, fileUpdateImageProfileUser).execute(tableLoginUserInfo.getUSRL_id(), ipAddress);
            } else {
                appCreaarte.showToast(getString(R.string.text_error_5));
            }
        } else {
            appCreaarte.showToast(getString(R.string.text_error_5));
        }
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
        dialogo.setTitle(R.string.text_variable_52);
        dialogo.setMessage(R.string.text_variable_53);

        dialogo.setPositiveButton(R.string.text_variable_51, (dialogInterface, i) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialogo.show();
    }

    private void showExplanation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.text_variable_52);
        builder.setMessage(R.string.text_variable_53);
        builder.setPositiveButton(R.string.text_variable_51, (dialog, which) -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.text_variable_50, (dialog, which) -> dialog.dismiss());
        builder.show();

    }

    /*private static String encode(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bao);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();

        }
        byte[] ba = bao.toByteArray();
        return Base64.encodeToString(ba, Base64.NO_WRAP);
    }*/

    /*private Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeigth / height;
        Matrix matrix = new Matrix();
        matrix.postRotate(90.0f);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
    }*/

}

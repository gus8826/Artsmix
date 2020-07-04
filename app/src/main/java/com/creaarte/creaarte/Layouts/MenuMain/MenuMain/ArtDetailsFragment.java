package com.creaarte.creaarte.Layouts.MenuMain.MenuMain;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.creaarte.creaarte.Controllers.AppCreaarte;
import com.creaarte.creaarte.Models.ItemArts;
import com.creaarte.creaarte.Models.ItemLike;
import com.creaarte.creaarte.R;
import com.creaarte.creaarte.StateSQLiteHelper.TableLoginUserInfo;
import com.creaarte.creaarte.WebService.Gets.GetArticleById;
import com.creaarte.creaarte.WebService.Sets.SetReaction;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ArtDetailsFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private TableLoginUserInfo tableLoginUserInfo;
    private AppCreaarte appCreaarte;
    private ItemArts itemArts;
    private ItemLike itemLike;
    private LinearLayout linearLayout2;
    private ScrollView scrollViewArtsDetails;
    private ImageView imageViewLikeArtsDetails;
    private ImageView imageViewArtsImageArtsDetails;
    private TextView textViewArtsNameArtsDetails;
    private TextView textViewAuthorNameArtsDetails;
    private TextView textViewProductCodeArtsDetails;
    private TextView textViewCategoryArtsDetails;
    private TextView textViewPriceArtsDetails;
    private TextView textViewDescriptionArtsDetails;
    private TextView textViewNumberLikeArtsDetails;
    private TextView textViewNumberCommentsArtsDetails;
    private Button buttonLikeArtsDetails;
    private ShimmerFrameLayout shimmerArtsDetals;

    private String ipAddress = "";
    //private String id_article = "";
    private Thread artsTread;

    //private Animator currentAnimator;
    //private int shortAnimationDuration;
    //private ImageView expandedImageView;

    public ArtDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewArtDetailsFragment = inflater.inflate(R.layout.fragment_art_details, container, false);
        activity = getActivity();
        appCreaarte = new AppCreaarte(activity);
        tableLoginUserInfo = new TableLoginUserInfo(activity);
        itemArts = new ItemArts();
        itemLike = new ItemLike();
        ConstraintLayout constraintLayoutArtsDetails = viewArtDetailsFragment.findViewById(R.id.constraintLayoutArtsDetails);
        scrollViewArtsDetails = viewArtDetailsFragment.findViewById(R.id.scrollViewArtsDetails);
        linearLayout2 = viewArtDetailsFragment.findViewById(R.id.linearLayout2);
        imageViewArtsImageArtsDetails = viewArtDetailsFragment.findViewById(R.id.imageViewArtsImageArtsDetails);
        imageViewLikeArtsDetails = viewArtDetailsFragment.findViewById(R.id.imageViewLikeArtsDetails);
        shimmerArtsDetals = viewArtDetailsFragment.findViewById(R.id.shimmerArtsDetals);
        textViewArtsNameArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewArtsNameArtsDetails);
        textViewAuthorNameArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewAuthorNameArtsDetails);
        textViewProductCodeArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewProductCodeArtsDetails);
        textViewCategoryArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewCategoryArtsDetails);
        textViewPriceArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewPriceArtsDetails);
        textViewDescriptionArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewDescriptionArtsDetails);
        textViewNumberLikeArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewNumberLikeArtsDetails);
        textViewNumberCommentsArtsDetails = viewArtDetailsFragment.findViewById(R.id.textViewNomberCommentsArtsDetails);
        Button buttonCommentsArtsDetails = viewArtDetailsFragment.findViewById(R.id.buttonCommentsArtsDetails);
        buttonCommentsArtsDetails.setOnClickListener(this);
        buttonLikeArtsDetails = viewArtDetailsFragment.findViewById(R.id.buttonLikeArtsDetails);
        buttonLikeArtsDetails.setOnClickListener(this);
        shimmerArtsDetals.startShimmer();

        /*ImageView thumb1View = viewArtDetailsFragment.findViewById(R.id.imageViewArtsImageArtsDetails);
        expandedImageView = viewArtDetailsFragment.findViewById(R.id.expanded_image);
        //findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        thumb1View.setOnClickListener(view -> zoomImageFromThumb(thumb1View));
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);*/
        Bundle bundle = getArguments();
        if (bundle != null) {
            //id_article = bundle.getString("id_article");
            getArticle(bundle.getString("id_article"));
        }

        return viewArtDetailsFragment;
    }

    private void getArticle(String id_article) {
        shimmerArtsDetals.startShimmer();
        artsTread = new Thread() {
            @Override
            public void run() {
                if (appCreaarte.isNetDisponible()) {
                    if (AppCreaarte.isOnlineNet()) {
                        if (AppCreaarte.isConnectedWifi(activity)) {
                            ipAddress = appCreaarte.getDeviceWifiData();
                        } else if (AppCreaarte.isConnectedMobile(activity)) {
                            ipAddress = appCreaarte.getDeviceipMobileData();
                        }
                        try {
                            itemArts = new GetArticleById(activity).execute(id_article, ipAddress, tableLoginUserInfo.getUSRL_id()).get();
                            activity.runOnUiThread(() -> reloadData());
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_5));
                    }
                } else {
                    appCreaarte.showToast(getString(R.string.text_error_5));
                }
                for (int i = 1; i <= 2; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                activity.runOnUiThread(() -> {
                    shimmerArtsDetals.stopShimmer();
                    shimmerArtsDetals.setVisibility(View.GONE);
                    scrollViewArtsDetails.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.VISIBLE);
                });

            }
        };
        artsTread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerArtsDetals.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerArtsDetals.stopShimmer();
        super.onPause();
    }

    private void reloadData() {
        if (!Objects.requireNonNull(itemArts).getARTW_img().isEmpty()) {
            Picasso.get().load(itemArts.getARTW_img().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(imageViewArtsImageArtsDetails);
        }

        if (!Objects.requireNonNull(itemArts).getARTW_name().isEmpty()) {
            textViewArtsNameArtsDetails.setText(itemArts.getARTW_name());
        }

        if (!Objects.requireNonNull(itemArts).getUSRL_alias().isEmpty()) {
            textViewAuthorNameArtsDetails.setText(itemArts.getUSRL_alias());
        }

        if (!Objects.requireNonNull(itemArts).getARTW_sku().isEmpty()) {
            textViewProductCodeArtsDetails.setText(itemArts.getARTW_sku());
        }

        if (!Objects.requireNonNull(itemArts).getCATG_name().isEmpty()) {
            textViewCategoryArtsDetails.setText(itemArts.getCATG_name());
        }

        if (!Objects.requireNonNull(itemArts).getARTW_price().isEmpty()) {
            textViewPriceArtsDetails.setText(itemArts.getARTW_price());
        }

        if (!Objects.requireNonNull(itemArts).getARTW_desc().isEmpty()) {
            textViewDescriptionArtsDetails.setText(itemArts.getARTW_desc());
        }

        if (!Objects.requireNonNull(itemArts).getARTW_comm().isEmpty()) {
            textViewNumberCommentsArtsDetails.setText(itemArts.getARTW_comm());
        }

        if (!Objects.requireNonNull(itemArts).getARTW_reac().isEmpty()) {
            textViewNumberLikeArtsDetails.setText(itemArts.getARTW_reac());
        }

        if (itemArts.getARTW_reac_flag().equals("0")) {
            buttonLikeArtsDetails.setTextColor(activity.getColor(R.color.Grey900));
            imageViewLikeArtsDetails.setImageResource(R.mipmap.ic_heart_outline_grey600_24dp);
        }

        if (itemArts.getARTW_reac_flag().equals("1")) {
            buttonLikeArtsDetails.setTextColor(activity.getColor(R.color.colorPrimary));
            imageViewLikeArtsDetails.setImageResource(R.mipmap.ic_heart_liked);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLikeArtsDetails:
                if (!tableLoginUserInfo.getUSRL_id().isEmpty()) {
                    if (appCreaarte.isNetDisponible()) {
                        if (AppCreaarte.isOnlineNet()) {
                            if (AppCreaarte.isConnectedWifi(activity)) {
                                ipAddress = appCreaarte.getDeviceWifiData();
                            } else if (AppCreaarte.isConnectedMobile(activity)) {
                                ipAddress = appCreaarte.getDeviceipMobileData();
                            }
                            try {
                                itemLike = new SetReaction(activity).execute("1", itemArts.getARTW_id(), tableLoginUserInfo.getUSRL_id(), ipAddress).get();
                                if (itemLike.getARTW_reac_flag().equals("0")) {
                                    buttonLikeArtsDetails.setTextColor(activity.getColor(R.color.Grey900));
                                    imageViewLikeArtsDetails.setImageResource(R.mipmap.ic_heart_outline_grey600_24dp);
                                }

                                if (itemLike.getARTW_reac_flag().equals("1")) {
                                    buttonLikeArtsDetails.setTextColor(activity.getColor(R.color.colorPrimary));
                                    imageViewLikeArtsDetails.setImageResource(R.mipmap.ic_heart_liked);
                                }
                                textViewNumberLikeArtsDetails.setText(itemLike.getARTW_reac());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            appCreaarte.showToast(getString(R.string.text_error_5));
                        }
                    } else {
                        appCreaarte.showToast(getString(R.string.text_error_5));
                    }
                } else {
                    showDialogRequest();
                }
                break;

            case R.id.buttonCommentsArtsDetails:
                Bundle bundle = new Bundle();
                bundle.putString("id_article", itemArts.getARTW_id());
                Navigation.findNavController(view).navigate(R.id.action_nav_art_details_fragment_to_nav_arts_comments_fragment, bundle);
                break;

            default:
                break;

        }
    }

    private void showDialogRequest() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_request);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView textViewTitleDialogRequest = dialog.findViewById(R.id.textViewTitleDialogRequest);
        textViewTitleDialogRequest.setText(R.string.app_name);

        TextView textViewTextInfotmationDialogRequest = dialog.findViewById(R.id.textViewTextInfotmationDialogRequest);
        textViewTextInfotmationDialogRequest.setText(R.string.text_variable_69);

        Button buttonClosedDialogRequest = dialog.findViewById(R.id.buttonClosedDialogRequest);
        buttonClosedDialogRequest.setText(R.string.text_variable_71);
        buttonClosedDialogRequest.setOnClickListener(v -> dialog.dismiss());

        Button buttonAceptDialogRequest = dialog.findViewById(R.id.buttonAceptDialogRequest);
        buttonAceptDialogRequest.setText(R.string.text_variable_70);
        buttonAceptDialogRequest.setOnClickListener(v -> {
            Navigation.findNavController(buttonLikeArtsDetails).navigate(R.id.action_nav_art_details_fragment_to_nav_login_options_fragment);
            dialog.dismiss();
        });
        dialog.show();
    }

    /*private void zoomImageFromThumb(final View thumbView*//*, int imageResId*//*) {
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }
        if (!Objects.requireNonNull(itemArts).getARTW_img().isEmpty()) {
            Picasso.with(activity).load(itemArts.getARTW_img().replace("..", AppCreaarte.BASE_IMAGE_URL)).into(expandedImageView);
        }
        //expandedImageView.setImageResource(imageResId);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        constraintLayoutArtsDetails.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(view -> {
            if (currentAnimator != null) {
                currentAnimator.cancel();
            }

            AnimatorSet set1 = new AnimatorSet();
            set1.play(ObjectAnimator
                    .ofFloat(expandedImageView, View.X, startBounds.left))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.Y, startBounds.top))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.SCALE_X, startScaleFinal))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.SCALE_Y, startScaleFinal));
            set1.setDuration(shortAnimationDuration);
            set1.setInterpolator(new DecelerateInterpolator());
            set1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    thumbView.setAlpha(1f);
                    expandedImageView.setVisibility(View.GONE);
                    currentAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    thumbView.setAlpha(1f);
                    expandedImageView.setVisibility(View.GONE);
                    currentAnimator = null;
                }
            });
            set1.start();
            currentAnimator = set1;
        });
    }*/

}

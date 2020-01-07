package com.example.allenvungletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.vungle.warren.*;
import com.vungle.warren.error.VungleException;


public class MainActivity extends Activity {

    @NonNull private  String name;
    @NonNull private  String placementReferenceId;
    @NonNull private  TextView titleTextView;
    @NonNull private  Button btn_load_interstitial_legacy,btn_load_rewarded_video,btn_load_mrec;
    @NonNull private Button btn_play_interstitial_legacy,btn_play_rewarded_video,btn_play_mrec;
    @NonNull private boolean nativeAdPlaying;
    @Nullable private  Button btn_pause_resume_mrec,btn_pause_resume_in_feed,btn_load_banner,btn_show_banner;
    @Nullable private  Button btn_close_mrec,btn_close_in_feed;
    @Nullable private  RelativeLayout container_in_feed,container_banner;


    String appId,iv,rv,mrec,indeed;
    public final String  TAG="allentest";
    private VungleNativeAd vungleNativeAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.

        setContentView(R.layout.activity_main);
        appId = getString(R.string.app_id);
        iv = getString(R.string.placement_id_interstitial_legacy);
        rv = getString(R.string.placement_id_rewarded_video);
        mrec = getString(R.string.placement_id_mrec);
        indeed = getString(R.string.placement_id_in_feed);
        initView();

        Vungle.init(appId,getApplicationContext(),new InitCallback() {
            @Override
            public void onSuccess() {
                // Initialization has succeeded and SDK is ready to load an ad or play one if there
                // is one pre-cached already
                //load banner test
                titleTextView.setText(R.string.app_id);

            }

            @Override
            public void onError(Throwable throwable) {
                titleTextView.setText(throwable.getLocalizedMessage());
                // Initialization error occurred - throwable.getLocalizedMessage() contains error message
            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {
                // Callback to notify when an ad becomes available for the cache optimized placement
                // NOTE: This callback works only for the cache optimized placement. Otherwise, please use
                // LoadAdCallback with loadAd API for loading placements.
                titleTextView.setText("onAutoCacheAdAvailable has "+placementId);

            }
        },null);



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


    }

    private void initView(){
        titleTextView=findViewById(R.id.text_app_id);
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(titleTextView.getText().toString());
            }
        });
        //load button
        btn_load_interstitial_legacy=findViewById(R.id.btn_load_interstitial_legacy);
        btn_load_rewarded_video=findViewById(R.id.btn_load_rewarded_video);
        btn_load_mrec=findViewById(R.id.btn_load_mrec);
        btn_load_banner=findViewById(R.id.btn_load_banner);
//        btn_load_banner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Banners.loadBanner("BANNER_ALLEN_TEST-6848913", AdConfig.AdSize.BANNER_LEADERBOARD, new LoadAdCallback() {
//                    @Override
//                    public void onAdLoad(String id) {
//                        makeToast("onAdLoad"+id);
//                    }
//
//                    @Override
//                    public void onError(String id, VungleException exception) {
//                        makeToast("onError"+exception.getLocalizedMessage());
//                    }
//                });
//            }
//        });
        //playbutton
        btn_play_interstitial_legacy=findViewById(R.id.btn_play_interstitial_legacy);
        btn_play_rewarded_video=findViewById(R.id.btn_play_rewarded_video);
        btn_play_mrec=findViewById(R.id.btn_play_mrec);
        btn_show_banner=findViewById(R.id.btn_play_banner);
        btn_show_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Banners.canPlayAd("BANNER_ALLEN_TEST-6848913", AdConfig.AdSize.BANNER_LEADERBOARD)) {
//                    VungleBanner vungleBanner = Banners.getBanner("BANNER_ALLEN_TEST-6848913", AdConfig.AdSize.BANNER_LEADERBOARD, new PlayAdCallback() {
//                        @Override
//                        public void onAdStart(String id) {
//
//                        }
//
//                        @Override
//                        public void onAdEnd(String id, boolean completed, boolean isCTAClicked) { }
//
//                        @Override
//                        public void onError(String id, VungleException exception) {
//                            makeToast("onError"+exception.getLocalizedMessage()+"onAdLoad");
//
//                        }
//                    });
//                    // Add VungleBanner to a view container
////                            Log.d("allen",vungleBanner+"adf"+container_banner);
//                    if (vungleBanner!=null){
//                        container_banner.addView(vungleBanner);
//                    }
//
//                }
            }
        });

        btn_pause_resume_mrec=findViewById(R.id.btn_pause_resume_mrec);

        btn_close_mrec=findViewById(R.id.btn_close_mrec);

        container_in_feed=findViewById(R.id.container_in_feed);
        container_banner=findViewById(R.id.container_banner);

        setLoadClick(btn_load_interstitial_legacy,iv);
        setLoadClick(btn_load_rewarded_video,rv);
        setLoadMrecClick(btn_load_mrec,mrec);



        setPlayClick(btn_play_interstitial_legacy,iv);
        setPlayClick(btn_play_rewarded_video,rv);

        setPlayMrecClick(btn_play_mrec,mrec);

//        setCloseClick(btn_close_in_feed);
        setCloseClick(btn_close_mrec);

        setPauseClick(btn_pause_resume_mrec);
//        setPauseClick(btn_pause_resume_in_feed);


    }
    private void loadAd(String placementReferenceId){
        if (Vungle.isInitialized()) {
            Vungle.loadAd(placementReferenceId,new LoadAdCallback() {
                @Override
                public void onAdLoad(String placementReferenceId) {
                    makeToast(placementReferenceId+"onAdLoad");
                }
                @Override
                public void onError(String placementReferenceId, Throwable throwable) {
                    // Load ad error occurred - throwable.getLocalizedMessage() contains error message
                    makeToast(placementReferenceId+"onError=="+throwable.getLocalizedMessage());
                }
            });
        }
    }
    private void playAd(String placementReferenceId){
        if (Vungle.canPlayAd(placementReferenceId)) {

            Vungle.playAd(placementReferenceId, null, new PlayAdCallback() {
                @Override
                public void onAdStart(String id) {

                }

                @Override
                public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {
makeToast("onAdEnd"+"completed is"+completed);
                }

                @Override
                public void onError(String id, Throwable exception) {
                    makeToast(placementReferenceId+"onError=="+exception.getLocalizedMessage());

                }
            });
        }else{
            makeToast(placementReferenceId+"can not play");
        }
    }

    private void setLoadClick(Button button, final String placementReferenceId){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
//                startActivity(intent);
                loadAd(placementReferenceId);
            }
        });
    }
    private void setPlayClick(Button button, final String placementReferenceId){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAd(placementReferenceId);
            }
        });
    }

    private void setLoadMrecClick(Button button, final String placementReferenceId){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                AdConfig adConfig = new AdConfig();
////                adConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC);
//
//                if (Vungle.isInitialized()) {
//                    Vungle.loadAd(placementReferenceId,adConfig,new LoadAdCallback() {
//                        @Override
//                        public void onAdLoad(String placementReferenceId) {
//                            makeToast(placementReferenceId+"onAdLoad");
//                        }
//                        @Override
//                        public void onError(String placementReferenceId, Throwable throwable) {
//                            // Load ad error occurred - throwable.getLocalizedMessage() contains error message
//                            makeToast(placementReferenceId+"onError=="+throwable.getLocalizedMessage());
//                        }
//                    });
//                }

            }
        });
    }

    private void setPlayMrecClick(Button button, final String placementReferenceId){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                AdConfig adConfig = new AdConfig();
//                adConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC);
//
//                vungleNativeAd = Vungle.getNativeAd(placementReferenceId, adConfig, vunglePlayAdCallback);
//                if (vungleNativeAd!=null){
//                    View nativeAdView = vungleNativeAd.renderNativeView();
//                    container_in_feed.addView(nativeAdView);
//                }else{
//                    makeToast("vungleNativeAd is null");
//                }

            }
        });
    }

    private void setCloseClick(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseNatveAd();
                isPlaying=false;
            }
        });
    }

    private void releaseNatveAd() {
        container_in_feed.removeAllViews();
//        vungleNativeAd.finishDisplayingAd();
        vungleNativeAd=null;
    }

    private boolean isPlaying=false;
    private void setPauseClick(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    vungleNativeAd.setAdVisibility(true);
                    isPlaying=false;
                }else{
                    vungleNativeAd.setAdVisibility(false);
                    isPlaying=true;
                }
            }
        });
    }

    private void makeToast(String message) {
        Log.e(TAG,message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
    private final LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
        @Override
        public void onAdLoad(String placementReferenceId) {
            // Placement reference ID for the placement to load ad assets
            makeToast("onAdLoa="+placementReferenceId);
        }

        @Override
        public void onError(String placementReferenceId, Throwable throwable) {
            // Placement reference ID for the placement that failed to download ad assets
            // Throwable contains error message
            makeToast("onError="+placementReferenceId+"=="+throwable.getLocalizedMessage());

        }
    };



//    // Implement PlayAdCallback
//    private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
//        @Override
//        public void onAdStart(String placementReferenceId) {
//            // Placement reference ID for the placement to be played
//            makeToast("onAdStart="+placementReferenceId);
//        }
//
//        @Override
//        public void onAdEnd (String placementReferenceId, boolean completed, boolean isCTAClicked) {
//            // Placement reference ID for the placement that has completed ad experience
//            // completed has value of true or false to notify whether video was
//            // watched for 80% or more
//            // isCTAClkcked has value of true or false to indicate whether download button
//            // of an ad has been clicked by the user
//            makeToast("onAdEnd="+placementReferenceId);
////            releaseNatveAd();
//            isPlaying=false;
//        }
//
//        @Override
//        public void onError(String placementReferenceId, Throwable exception) {
//            // Placement reference ID for the placement that failed to play an ad
//            // Throwable contains error message
//            makeToast("onError="+placementReferenceId+"=="+exception.getLocalizedMessage());
//            isPlaying=false;
//        }
//    };
}

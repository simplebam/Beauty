package com.yueyue.mybeauty.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yueyue.mybeauty.MainActivity;
import com.yueyue.mybeauty.R;
import com.yueyue.mybeauty.config.Constants;
import com.yueyue.mybeauty.model.DataBase;
import com.yueyue.mybeauty.model.Image;
import com.yueyue.mybeauty.utils.Imager;
import com.yueyue.mybeauty.utils.SpUtil;

import java.util.Date;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int SPLASH_DURATION = 1800;
    private static final int SPLASH_DURATION_SHORT = 1500;
    private static final String SPLASH = "splash";

    //管理系统UI之四：使用全屏沉浸模式（Using Immersive Full-Screen Mode） - 泡在网上的日子
    //               http://www.jcodecraeer.com/a/anzhuokaifa/developer/2014/1117/1997.html
    public static int FULL_SCREEN_UI = View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private static String type;

    private ImageView mIvSplash;

    public static void updateSplash(String type, boolean force) {
        Date now = new Date();
        //表示10 * 60 * 1000表示10分钟
        boolean needUpdate = (now.getTime() - SpUtil.getLong(Constants.DATE)) > 10 * 60 * 1000;
        if (needUpdate || force) {
            String url = DataBase.getRandomImage(type);
            if (TextUtils.isEmpty(url)) return;

            SpUtil.save(Constants.DATE, now.getTime());
            SpUtil.save(SPLASH, url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(FULL_SCREEN_UI);
        //设置imageView为全屏
//        ViewGroup.LayoutParams layoutParams = new ViewGroup
//                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        ivSplash.setLayoutParams(layoutParams);
        setContentView(R.layout.activity_splash);
        mIvSplash = findViewById(R.id.splash_background);
        mIvSplash.setScaleType(ImageView.ScaleType.CENTER_CROP);

        type = SpUtil.getString(SettingFragment.RANDOM_SPLASH);
        if (TextUtils.isEmpty(type)) type = "0";

        if ("origin".equals(type)) {
            getWindow().setBackgroundDrawableResource(R.drawable.splash);
            startAppDelay(SPLASH_DURATION_SHORT);
        } else {
            updateSplash(type, false);
            loadImage();
        }
    }


    private void startAppDelay(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, delay);
    }

    private void loadImage() {
        String url = SpUtil.getString(SPLASH);
        if (TextUtils.isEmpty(url)) {
            Glide.with(this).load(R.drawable.splash).crossFade().into(mIvSplash);
            updateSplash(type, false);
        } else if (DataBase.findImageByUrl(url) == null) {
            Glide.with(this).load(R.drawable.splash).crossFade().into(mIvSplash);
        } else {
            Image image = DataBase.findImageByUrl(url);
            Imager.loadWithHeader(this, image, mIvSplash);
        }
        startAppDelay(SPLASH_DURATION_SHORT);
    }

    @Override
    public void onBackPressed() {
        //disable back button when showing splash
    }


}

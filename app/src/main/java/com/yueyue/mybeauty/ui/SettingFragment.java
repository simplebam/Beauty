package com.yueyue.mybeauty.ui;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.bugtags.library.Bugtags;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yueyue.mybeauty.R;
import com.yueyue.mybeauty.base.App;
import com.yueyue.mybeauty.config.Constants;
import com.yueyue.mybeauty.model.DataBase;
import com.yueyue.mybeauty.utils.AppUtil;
import com.yueyue.mybeauty.utils.SnackUtil;
import com.yueyue.mybeauty.utils.SpUtil;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String TAG = SettingFragment.class.getSimpleName();
    public static final String CLEAR_CACHE = "clear_cache";
    public static final String FEED_BACK = "feedback";
    public static final String CHECK_VERSION = "check_version";
    public static final String RANDOM_SPLASH = "random_splash";
    public static final String LIKE_DOWNLOAD = "like_download";
    public static final String SECRET_MODE = "secret_mode";
    public static final String THEME_COLOR = "theme_color";
    public static final String ABOUT = "about";
    private static final long DURATION = 300;
    private Preference clearCache;
    private EditTextPreference feedback;
    private Preference version;
    private Preference splash;
    private Preference about;

    private View rootView;
    private long startTime;
    private boolean first = true;
    private int secretIndex;
    private Preference theme;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initFindResource();
        refreshCache();
        initListener();

    }

    private void initFindResource() {
        clearCache = findPreference(CLEAR_CACHE);
        feedback = (EditTextPreference) findPreference(FEED_BACK);
        about = findPreference(ABOUT);
        version = findPreference(CHECK_VERSION);
        splash = findPreference(RANDOM_SPLASH);
        //        theme = findPreference(THEME_COLOR);
    }

    private void refreshCache() {
        String cache = String.format(getString(R.string.set_current_cache) + " %s", getDataSize());
        clearCache.setSummary(cache);
    }

    private String getDataSize() {
        File file = App.getAppContext().getCacheDir();
        return FileUtils.getDirSize(file);
    }


    private void initListener() {
        //Preference 三种监听事件说明 - wangjicong_215的博客 - CSDN博客
        //         http://blog.csdn.net/wangjicong_215/article/details/52209380
        splash.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SplashActivity.updateSplash((String) newValue, true);
                return true;
            }
        });

        //Android M动态申请获取权限android.permission.READ_PHONE_STATE - fenggering的博客 - CSDN博客
        //     http://blog.csdn.net/fenggering/article/details/53432401
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new RxPermissions(getActivity()).request(Manifest.permission.READ_PHONE_STATE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<Boolean, String>() {
                            @Override
                            public String call(Boolean granted) {
                                if (granted) {
                                    return EncryptUtils.encryptMD5ToString(PhoneUtils.getIMEI(), Constants.DANTE);
                                }
                                return null;
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String device) {
                                if (TextUtils.isEmpty(device)) {
                                    SnackUtil.showSnack(rootView, R.string.permission_statement);
                                } else {
                                    SpUtil.save("deviceId", device);
                                    AppUtil.donate(getActivity());
                                }
                            }
                        });
                return true;
            }
        });

        clearCache.setOnPreferenceClickListener(this);

        feedback.setOnPreferenceChangeListener((preference, newValue) -> {
            if (TextUtils.isEmpty((CharSequence) newValue)) return false;
            Bugtags.sendFeedback((String) newValue);
            SnackUtil.showSnack(rootView, R.string.thanks_for_feedback);
            return true;
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (null == rootView) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        switch (key) {
            case CLEAR_CACHE:
                Observable.just(clearCache())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean success) {
                                if (success) {
                                    refreshCache();
                                    Snackbar.make(rootView, R.string.clear_finished, Snackbar.LENGTH_LONG)
                                            .setAction(R.string.deep_clean, view -> AppUtil.openAppInfo(getActivity())).show();
                                } else {
                                    SnackUtil.showSnack(rootView, R.string.clear_cache_failed);
                                }
                            }
                        });

                break;
        }
        return true;
    }

    private boolean clearCache() {
        DataBase.clearAllImages();
//        SpUtil.clear();
        return CleanUtils.cleanInternalCache();
    }


    private void secretStepTwo() {
        if (System.currentTimeMillis() - startTime < DURATION * (secretIndex + 1)) {
            if (secretIndex > 2) {
                Log.i("test", "splash " + secretIndex);
                secretIndex++;
            }
        }
        if (secretIndex == 6) {
            if (SpUtil.getBoolean(SECRET_MODE)) {
                SpUtil.save(SECRET_MODE, false);
                secretIndex = 0;
                SnackUtil.showSnack(rootView, R.string.secret_mode_closed);
            } else {
                SpUtil.save(SECRET_MODE, true);
                secretIndex = 0;
                SnackUtil.showSnackLong(rootView, R.string.secret_mode_opened);
            }
            secretIndex++;
        }
    }

    private void secretStepOne() {
        if (first) {
            startTime = System.currentTimeMillis();
            first = false;
        }
        if (System.currentTimeMillis() - startTime < DURATION * (secretIndex + 1)) {
            if (secretIndex < 3) {
                secretIndex++;
            }
        }
    }

    private void sendEmailFeedback() {
        //This is wired, I used ACTION_SENDTO at first
        //but check intent returns unsafe
        //so I change to ACTION_VIEW (like the system do)
        Intent email = new Intent(Intent.ACTION_SENDTO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            email = new Intent(Intent.ACTION_VIEW);
        }
        if (AppUtil.isIntentSafe(email)) {
            email.setData(Uri.parse("mailto:danteandroi@gmail.com"));
            email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Feedback");
            email.putExtra(Intent.EXTRA_TEXT, "Hi，");
            startActivity(email);
        } else {
            SnackUtil.showSnack(rootView, R.string.email_not_install);
        }
    }

}

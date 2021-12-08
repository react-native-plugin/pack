package com.reactnativepluginpack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;


import com.facebook.react.ReactActivity;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.facebook.react.bridge.JSIModulePackage;

/**
 * PackActivityDelegate
 */
public class PackActivityDelegate extends ReactActivityDelegate {

  public final @Nullable
  Activity mActivity;
  public String mMainComponentName;
  public String mJSBundleFile;
  public Bundle mSavedInstanceState = new Bundle();

  private ReactNativeHost mReactNativeHost;

  public PackActivityDelegate(ReactActivity activity, @Nullable String mainComponentName, @Nullable String JSBundleFile) {
    super(activity, mainComponentName);
    mActivity = activity;
    mMainComponentName = mainComponentName;
    mJSBundleFile = JSBundleFile;
  }

  @Override
  public String getMainComponentName() {
    if (mActivity instanceof PackActivity) {
      String mName = ((PackActivity) mActivity).getMainComponentName();
      if (mName != null && !mName.isEmpty()) {
        mMainComponentName = mName;
        return mName;
      }
    }
    return mMainComponentName;
  }

  public String getJSBundleFile() {
    if (mActivity instanceof PackActivity) {
      String mName = ((PackActivity) mActivity).getJSBundleFile();
      if (mName != null && !mName.isEmpty()) {
        mJSBundleFile = mName;
        return mName;
      }
    }
    return mJSBundleFile;
  }

  public Integer getStyle() {
    if (mActivity instanceof PackActivity) {
      int mName = ((PackActivity) mActivity).getStyle();
      return mName;
    }
    return 1;
  }

  @Override
  protected Bundle getLaunchOptions() {
    if (mActivity instanceof PackActivity) {
      Bundle mBundle = ((PackActivity) mActivity).getExtraData();
      return mBundle;
    }
    return null;
  }

  @Override
  protected ReactRootView createRootView() {
    Log.w("PackActivityDelegate", "createRootView");
    ReactRootView reactRootView = null;
    if (mActivity instanceof PackActivity) {
      reactRootView = ((PackActivity) mActivity).createRootView();
    } else {
      reactRootView = super.createRootView();
    }
    return reactRootView;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    mSavedInstanceState = savedInstanceState;
    Log.i("PackActivityDelegate", "onCreate start mMainComponentName " + getMainComponentName() + " mBundleFile " + getJSBundleFile() + " mStyle " + getStyle());
    super.onCreate(savedInstanceState);
    Log.w("PackActivityDelegate", "onCreate end");
  }

//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    String mainComponentName = getMainComponentName();
//    String bundleFile = getJSBundleFile();
//    Log.i("PackActivityDelegate", "mMainComponentName " + mainComponentName + " mBundleFile " + bundleFile);
//    this.mReactDelegate = new ReactDelegate(
//      getPlainActivity(), getReactNativeHost(), mainComponentName, getLaunchOptions());
//    if (mainComponentName != null) {
//      loadApp(mainComponentName);
//    }
//  }

//  @Override
//  protected void loadApp(String appKey) {
//    super.loadApp(appKey);
//  }

  @Override
  public ReactNativeHost getReactNativeHost() {
    Log.w("PackActivityDelegate", "ReactNativeHost getReactNativeHost");
    if (mReactNativeHost != null) {
      return mReactNativeHost;
    }

    // 判断加载方式
    if (getStyle() == 2) {
      // 注册方式加载
      mReactNativeHost = super.getReactNativeHost();
      return mReactNativeHost;
    }

    mReactNativeHost = new ReactNativeHost(getPlainActivity().getApplication()) {

      @Override
      public boolean getUseDeveloperSupport() {
        return false;
      }

      @Override
      protected List<ReactPackage> getPackages() {
        List<ReactPackage> packages = new ArrayList<>();
        List<ReactPackage> modulePackages = PackPackage.getModulePackages();
        if (modulePackages != null) {
          packages.addAll(modulePackages);
        }
        return packages;
      }

      @Override
      protected String getJSMainModuleName() {
        return "index";
      }

      @Override
      protected JSIModulePackage getJSIModulePackage() {
        JSIModulePackage jsiModulePackage = PackPackage.getJsiModulePackage();
        if (jsiModulePackage != null) {
          return jsiModulePackage;
        }
        return null;
      }

      @Override protected @Nullable
      String getJSBundleFile() {
        String bundleFile = ((PackActivity) getPlainActivity()).getJSBundleFile();
        Log.w("ReactNativeHost", "getReactNativeHost getJSBundleFile " + bundleFile);
        return bundleFile;
      }
    };
//    mReactNativeHost = new ReactNativeHostWrapper(getPlainActivity().getApplication(), mReactNativeHost);
    return mReactNativeHost;
  }


  protected void onDestroy() {
    super.onDestroy();
    mReactNativeHost.clear();
    mReactNativeHost = null;
  }

}

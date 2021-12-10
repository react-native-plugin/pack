package com.reactnativepluginpack;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

import java.util.HashMap;
import java.util.Map;

@ReactModule(name = PackModule.NAME)
public class PackModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Pack";

    public PackModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a * b);
    }

    public static native int nativeMultiply(int a, int b);

    /**
   * 打开跳转到RN的展示页面
   *
   * @param readableMap
   */
  @ReactMethod
  public void openPage(ReadableMap readableMap, final Promise promise) {
    try {
      if (readableMap == null) {
        Toast.makeText(getCurrentActivity(), "模块参数错误", Toast.LENGTH_SHORT).show();
        promise.reject("RN_OPEN_PAGE_ACTIVITY_ERROR", "readableMap isEmpty");
        return;
      }
      // 获取参数
      Integer style = readableMap.getInt("style");
      Boolean isReload = readableMap.getBoolean("isReload");
      String bundleUrl = readableMap.getString("bundleUrl");
      String appModule = readableMap.getString("appModule");
      String appName = readableMap.getString("appName");
      String appLogo = readableMap.getString("appLogo");
      String appVersion = readableMap.getString("appVersion");
      String appText = readableMap.getString("appText");
      ReadableMap extraData = readableMap.getMap("extraData");
      // 参数默认值
      style = style == null ? 1 : style;
      isReload = isReload == null ? false : isReload;
      bundleUrl = bundleUrl == null || bundleUrl.isEmpty() ? "" : bundleUrl;
      appModule = appModule == null || appModule.isEmpty() ? "" : appModule;
      appName = appName == null || appName.isEmpty() ? "" : appName;
      appLogo = appLogo == null || appLogo.isEmpty() ? "" : appLogo;
      appVersion = appVersion == null || appVersion.isEmpty() ? "" : appVersion;
      appText = appText == null || appText.isEmpty() ? "" : appText;
      extraData = extraData == null ? null : extraData;
      if (appModule == null || appModule.isEmpty()) {
        Toast.makeText(getReactApplicationContext(), "模块名称不能为空", Toast.LENGTH_SHORT).show();
        promise.reject("RN_OPEN_PAGE_ACTIVITY_ERROR", "moduleName isEmpty");
        return;
      }
      if (bundleUrl == null || bundleUrl.isEmpty()) {
        Toast.makeText(getReactApplicationContext(), "模块加载地址不能为空", Toast.LENGTH_SHORT).show();
        promise.reject("RN_OPEN_PAGE_ACTIVITY_ERROR", "bundleUrl isEmpty");
        return;
      }

      // 传递额外参数序列化
      Bundle extraBundle = new Bundle();
      if (extraData != null) {
        extraBundle = Arguments.toBundle(extraData);
      }
      // 启动加载页面
      PackActivity.start(getCurrentActivity(), style, isReload, bundleUrl, appModule, appName, appLogo, appVersion,
        appText, extraBundle);
      promise.resolve(true);
      return;
    } catch (Exception e) {
      Toast.makeText(getReactApplicationContext(), "加载模块异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * 重新加载页面
   */
  @ReactMethod
  public void restartPage() {
    PackActivity packActivity = (PackActivity) getCurrentActivity();
    packActivity.restartActivity();
  }

  /**
   * 关闭当前页面
   */
  @ReactMethod
  public void finishPage() {
    PackActivity packActivity = (PackActivity) getCurrentActivity();
    packActivity.finishActivity();
  }

  /**
   * 触发监听数据
   * 发送给主框架
   */
  @ReactMethod
  public void emit(String eventName, String rMap) {
    _emit(eventName, rMap);
  }

  @ReactMethod
  public void emit(String eventName, ReadableMap readableMap) {
    WritableMap map = Arguments.createMap();
    map.merge(readableMap);
    _emit(eventName, map);
  }
  private void _emit(String eventName, Object obj) {
    try {
      Log.w("PackActivity", "emit sendEvent sss:" + eventName + "  " + obj.toString());
      ((ReactApplication) this.getCurrentActivity().getApplication()).getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, obj);
      Log.w("PackActivity", "emit sendEvent eee:" + eventName);
    } catch (Exception e) {
      Log.w("PackActivity", "emit error " + e.getMessage());
    }
  }
}

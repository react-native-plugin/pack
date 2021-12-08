package com.reactnativepluginpack;

import androidx.annotation.NonNull;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackPackage implements ReactPackage {

  public static List<ReactPackage> modulePackages = new ArrayList<>();
  public static JSIModulePackage jsiModulePackage = null;

  @NonNull
  @Override
  public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();
    modules.add(new PackModule(reactContext));
    return modules;
  }

  @NonNull
  @Override
  public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  // 设置加载的native模块包
  public static void setModulePackages(ReactApplication reactContext, final List<ReactPackage> _modulePackages) {
    List<ReactPackage> packages = new ArrayList<>();
    if (_modulePackages != null) {
      packages.addAll(_modulePackages);
    } else {
      if(reactContext !=null) {
        List<ReactPackage> _packages = reactContext.getReactNativeHost().getReactInstanceManager().getPackages();
        if(_packages != null) {
          packages.addAll(_packages);
        }
      }
    }
    modulePackages = packages;
    return;
  }

  // 获取加载的native模块包
  public static List<ReactPackage> getModulePackages() {
    List<ReactPackage> packages = new ArrayList<>();
    if (modulePackages != null) {
      return modulePackages;
    }
    return packages;
  }


  // 设置加载的jsi模块包
  public static void setJsiModulePackage(ReactApplication reactContext, final JSIModulePackage _jsiModulePackage) {
    if (_jsiModulePackage != null) {
      jsiModulePackage = _jsiModulePackage;
    }
    return;
  }

  // 获取加载的jsi模块包
  public static JSIModulePackage getJsiModulePackage() {
    if (jsiModulePackage != null) {
      return jsiModulePackage;
    }
    return null;
  }
}

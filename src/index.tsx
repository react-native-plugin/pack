import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package '@react-native-plugin/pack' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const Pack = NativeModules.Pack
  ? NativeModules.Pack
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return Pack.multiply(a, b);
}

interface PageConfig {
  style: number; // 加载方式1=默认新页面，2=当前页面进行加载
  isReload: boolean; // 是否强制重新加载，会重新下载包
  bundleUrl: string; // 包文件地址，支持bundle、js、zip格式的
  appModule: string; // 模块注册的模块名称
  appName: string; // 模块的启动页面名称
  appLogo: string; // 模块的启动页面logo
  appVersion: string; // 模块的版本
  appText: string; // 模块的启动页面底部显示文字
  extraData: any; // 模块的初始化附加数据，会自动返回在界面页面的props
}

// 打开新页面
export function openPage(config: PageConfig): Promise<any> {
  // todo ios暂未开发
  if (Platform.OS === 'ios') {
    return Promise.resolve(false);
  }
  return Pack.openPage(config);
}

// 重启当前页面
export function restartPage(): void {
  // todo ios暂未开发
  if (Platform.OS === 'ios') {
    return;
  }
  return Pack.restartPage();
}

// 结束关闭当前页面
export function finishPage(): void {
  // todo ios暂未开发
  if (Platform.OS === 'ios') {
    return;
  }
  return Pack.finishPage();
}

// 触发主模块事件
export function emit(eventName: String, obj: any): void {
  // todo ios暂未开发
  if (Platform.OS === 'ios') {
    return;
  }
  return Pack.emit(eventName, obj);
}

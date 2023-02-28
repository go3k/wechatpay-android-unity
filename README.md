# wechatpay-android-unity
Wechat pay SDK plugin for unity, support android platform only.

对微信SDK的支付部分进行Unity封装，让SDK的使用直接面向C#，简单易用。

## Usage

1. 下载wechatpay.unitypackage，导入到Unity即可；如果你的工程中已经存在External Dependency Manager根据你自己的情况选择是否导入该目录。
2. 如果你在使用Unity 2019及以下版本，需要把WechatPay.androidlib目录剪切到`Plugins/Android`下。
3. 接口都封装在`WXPay.cs`中，先调用`InitSDK`传入appid进行初始化，然后就可以使用`Pay`接口进行支付了。

---
1. Download the WeChatPay.unitypackage and import it into your project. If you already have the External Dependency Manager plugin in your project, choose whether or not to import this folder according to your needs.
2. If you are using Unity 2019 or an earlier version, you need to move the WeChatPay.androidlib folder into the `Plugins/Android` directory.
3. The `WXPay.cs` file defines all the available APIs. Call the `InitSDK` function and pass in your app ID, then you can use the `Pay` function to open the payment dialog.
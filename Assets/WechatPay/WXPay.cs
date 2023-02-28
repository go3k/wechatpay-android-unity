using System;
using UnityEngine;
public class WXPay : MonoBehaviour
{
    [Serializable]
    public class JsonObject
    {
        public int result;
        public string err;
    }
    
    private static readonly string _OBJ_NAME = "WXPayHelper";
    private const string _JAVA_CLASS = "com.wechatpay.WXBridge";

    private static WXPay _instance = null;
    public static WXPay Instance
    {
        get
        {
            if (null == _instance)
            {
                GameObject go = new GameObject(_OBJ_NAME);
                DontDestroyOnLoad(go);
                _instance = go.AddComponent<WXPay>();
            }
            return _instance;
        }
    }
    
    public void InitSDK(string appid)
    {
        ExecutionAndroidApi(_JAVA_CLASS, "InitSDK", appid, _OBJ_NAME);
    }

    private static Action<JsonObject> paymentCallback;
    public void Pay(string partnerId, string prepayId, string nonceStr, string timeStamp, string sign, string ExtData,
         Action<JsonObject> callback = null)
    {
        paymentCallback = callback;
        ExecutionAndroidApi(_JAVA_CLASS, "Pay", partnerId, prepayId, nonceStr, timeStamp, sign, ExtData);
    }

    public void PaymentCallback(string jsonMsg)
    {
        var json = JsonUtility.FromJson<JsonObject>(jsonMsg);
        if (paymentCallback != null)
        {
            paymentCallback(json);
        }
    }
    
    
    public static void ExecutionAndroidApi(string javaClass, string apiName, params object[] args)
    {
#if UNITY_ANDROID
        using (AndroidJavaClass cls = new AndroidJavaClass(javaClass))
        {
            cls.CallStatic(apiName, args);
        }
#endif
    }
}
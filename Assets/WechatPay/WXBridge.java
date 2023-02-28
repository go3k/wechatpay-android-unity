package com.wechatpay;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unity3d.player.UnityPlayer;

public class WXBridge {
    private static IWXAPI WXApi;
    public static String WechatID;
    
    private static String U3DNotifyClass;

    private static void unity3dSendMessage(String json) {
        UnityPlayer.UnitySendMessage(U3DNotifyClass, "PaymentCallback", json);
    }
    
    //WECHAT
    public static void InitSDK(String id, final String u3DNotifyClassName) {
        try {
            WechatID = id;
            U3DNotifyClass = u3DNotifyClassName;

            if (WXApi == null) {
                WXApi = WXAPIFactory.createWXAPI(UnityPlayer.currentActivity, id, false);
                WXApi.registerApp(id);
            }
        } catch (Exception e) {
            WXApi = null;
        }
    }
    
    public static void Pay(final String partnerId, final String prepayId, final String nonceStr, final String timeStamp, final String sign, final String extData) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	try {
                    if (WXApi != null) {
                    	if(WXApi.isWXAppInstalled())
                    	{
                    		PayReq request = new PayReq();
                        	request.appId = WechatID;
                        	request.partnerId = partnerId;
                        	request.prepayId = prepayId;
                        	request.packageValue = "Sign=WXPay";
                        	request.nonceStr = nonceStr;
                        	request.timeStamp = timeStamp;
                        	request.sign = sign;
                        	request.extData = extData;
                        	WXApi.sendReq(request);
                    	}
                    	else
                    	{
                    		JSONObject jo = new JSONObject();
                            try {
                                jo.put("result", -1);
                                jo.put("err", "Error: No wechat installed.");
                                unity3dSendMessage(jo.toString());
                            } catch (JSONException e) {
                            }
                    	}
                    }
                } catch (Exception e) {
                }
            }
        });
    }
    
    public static void PaySuccess() {
    	try {
			JSONObject jo = new JSONObject();
            jo.put("result", 0);
            unity3dSendMessage(jo.toString());
        } catch (JSONException e) {
        }
    }
    
    public static void PayFail(final int errorId) {
    	try {
			JSONObject jo = new JSONObject();
            jo.put("result", -1);
            jo.put("err", "Error: payment failed.");
            unity3dSendMessage(jo.toString());
        } catch (JSONException e) {
        }
    }
}

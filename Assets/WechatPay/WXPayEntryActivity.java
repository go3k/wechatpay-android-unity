package com.camelgames.ig.zha.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wechatpay.WXBridge;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI _wxapi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            _wxapi = WXAPIFactory.createWXAPI(this, WXBridge.WechatID);
            _wxapi.handleIntent(getIntent(), this);
        } catch (Exception e) {
        	
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        try {
            _wxapi.handleIntent(intent, this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        WXBridge.PaySuccess();
                        break;
                    default:
                        WXBridge.PayFail(resp.errCode);
                        break;
                }
                break;
            default:
                ; // do nothing
                break;
        }
        WXPayEntryActivity.this.finish();
    }
}
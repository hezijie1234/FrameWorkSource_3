package com.zijie.keepalive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by hezijie on 2019/7/19.
 * 一像素保活广播接收器
 */

public class KeepBroad extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action,Intent.ACTION_SCREEN_OFF)){
            KeepManager.getmInstance().startKeep(context);
        }else if (TextUtils.equals(action,Intent.ACTION_SCREEN_ON)){
            KeepManager.getmInstance().finishActivity();
        }
    }
}

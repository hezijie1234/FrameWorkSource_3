package com.zijie.keepalive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Keep;

import java.lang.ref.WeakReference;

/**
 * Created by hezijie on 2019/7/19.
 */

public class KeepManager {

    private static final KeepManager mInstance = new KeepManager();
    private KeepBroad keepBroad;
    private WeakReference<Activity> keepActivity;

    public static KeepManager getmInstance() {
        return mInstance;
    }

    public void registerKeepBroad(Context context) {
        keepBroad = new KeepBroad();
        IntentFilter intent = new IntentFilter();
        intent.addAction(Intent.ACTION_SCREEN_OFF);
        intent.addAction(Intent.ACTION_SCREEN_ON);
        context.registerReceiver(keepBroad, intent);
    }

    public void unRegisterKeepBroad(Context context) {
        if (null != keepBroad)
            context.unregisterReceiver(keepBroad);
    }

    /**
     * 单例模式容易造成内存泄露，所以使用弱引用。
     * @param activity
     */
    public void setActivity(KeepActivity activity){
        keepActivity = new WeakReference<Activity>(activity);

    }

    public void startKeep(Context context){
        Intent intent = new Intent(context, KeepActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finishActivity(){
        if (null != keepActivity){
            KeepActivity activity = (KeepActivity) keepActivity.get();
            if (null != activity){
                activity.finish();
            }
        }
    }
}

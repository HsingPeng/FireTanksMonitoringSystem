package cn.edu.njupt.tanksms.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.widget.MyProcessDialog;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGW;

/**
 * BaseActivity
 */
public abstract class BaseActivity extends ActionBarActivity {

    private MyProcessDialog myprocessdialog;
    private BroadcastReceiver receiver;
    private boolean isForeground = false ;
    private String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myprocessdialog = new MyProcessDialog(this);
        myprocessdialog.setCancelable(false);
        setBroadcast();
        LOGW(TAG,"onCreate()");
    }

    /**
     * 设置默认broadcast，设置前需要配置好onReceive()方法
     */
    private void setBroadcast() {

        String[] action = onReceive(this,new Intent());

        if(action != null){
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    BaseActivity.this.onReceive(context,intent);
                }
            };
            IntentFilter filter = new IntentFilter();
            for(String arg0 : action){
                filter.addAction(arg0);
            }
            registerReceiver(receiver , filter);  // 动态注册BroadcastReceiver
        }



    }

    /**
     *设置默认broadcast的onReceive方法
     * @param context
     * @param intent
     * @return String[] 指定broadcast的Action，可设置多个Action，null则为不设置broadcast
     */
    protected abstract String[] onReceive(Context context, Intent intent);

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if(receiver != null){
            unregisterReceiver(receiver);
        }

    }

    @Override
    public void finish(){
        if(myprocessdialog.isShowing()){
            dismissDialog();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    BaseActivity.super.finish();
                }
            },200);
        }else{
            super.finish();
        }
    }

    /**
     * 设置layout并设置好toolbar
     * @param res
     */
    protected void setView(int res){
        setContentView(res);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void showDialog(){
        if(! this.isFinishing()&&!myprocessdialog.isShowing()){
            myprocessdialog.show();
        }
    }

    public void dismissDialog(){
        if(myprocessdialog.isShowing()){
            myprocessdialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isForeground = true ;
        LOGW(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        isForeground = false ;
        LOGW(TAG, "onStop()");
    }

    /**
     * 是否在最前端
     * @return
     */
    protected boolean isForeground(){
        LOGW(TAG,"isForeground=>"+isForeground);
        return isForeground ;
    }

    /**
     * 替换掉缺省TAG
     * @param TAG
     */
    protected void setTAG(String TAG){
        this.TAG = TAG ;
    }

}

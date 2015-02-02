package cn.edu.njupt.tanksms.task;

import android.content.Context;
import android.content.Intent;

import java.net.HttpURLConnection;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.util.HttpUtils;

public class LogoutTask extends BaseTask {

    public LogoutTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        HttpUtils http = new HttpUtils(Config.URL+Config.URL_LOGOUT,context,new HttpUtils.HttpCallBack() {
            @Override
            public void onSuccess(int responseCode, String result, HttpURLConnection conn) {

            }

            @Override
            public void onFailure(Exception e, String msg) {

            }
        });
        http.get();
        http.cleanCookie();

        sendBroadcast();

    }

    private void sendBroadcast() {
        Intent intent = new Intent(Config.MAIN_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_LOGOUT);
        context.sendBroadcast(intent);
    }
}

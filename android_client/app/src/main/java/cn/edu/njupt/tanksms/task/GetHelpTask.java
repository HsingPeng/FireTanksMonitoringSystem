package cn.edu.njupt.tanksms.task;

import android.content.Context;
import android.content.Intent;

import java.net.HttpURLConnection;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.util.HttpUtils;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;


public class GetHelpTask extends BaseTask {

    private static final String TAG = "GetHelpTask";

    public GetHelpTask(Context context) {
        super(context);
    }

    @Override
    public void run() {

        final String[] result_text = new String[2];

        HttpUtils http = new HttpUtils(Config.URL+Config.URL_HELP,context, new HttpUtils.HttpCallBack() {
            @Override
            public void onSuccess(int responseCode, String result, HttpURLConnection conn) {
                LOGI(TAG,"responseCode=>"+responseCode+"result=>"+result.length());
                if(responseCode == 200){
                    result_text[0] = Config.SUCCESS ;
                    result_text[1] = result ;
                }else if(responseCode == 302 || responseCode == 304){
                    result_text[0] = Config.CODE_302;
                }else{
                    result_text[0] = context.getString(R.string.require_error) ;
                }
            }

            @Override
            public void onFailure(Exception e, String msg) {
                if(msg != null){
                    result_text[0] = msg ;
                }else{
                    result_text[0] = context.getString(R.string.server_connect_error) ;
                }
            }
        });
        //http.addHeader("Content-Type", "application/x-www-form-urlencoded");

        http.get();

        setBroadcast(result_text);

    }

    private String handlePage(String data) {
        int need_start = data.indexOf("<!-- BEGIN PAGE -->");
        int need_end = data.indexOf("<!-- END PAGE -->");
        int delete_start = data.indexOf("<!-- BEGIN HEADER -->");
        int delete_end = data.indexOf("<!-- END FOOTER -->");
        String before = data.substring(0,delete_start);
        String middle = data.substring(need_start,need_end);
        String after = data.substring(delete_end,data.length());
        return before + middle + after;
    }

    private void setBroadcast(String[] result_text) {
        Intent intent = new Intent();
        intent.setAction(Config.HELP_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_GET_HELP);
        if(result_text[0].equals(Config.SUCCESS)){
            intent.putExtra(Config.TASK_FLAG,result_text[0]);
            intent.putExtra(Config.TASK_DATA, handlePage(result_text[1]));

        }else{
            intent.putExtra(Config.TASK_FLAG,Config.FAILURE);
            intent.putExtra(Config.TASK_DATA,result_text[0]);
        }

        context.sendBroadcast(intent);
    }
}

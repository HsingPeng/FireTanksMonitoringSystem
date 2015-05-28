package cn.edu.njupt.tanksms.task;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.net.HttpURLConnection;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.model.MainListItem;
import cn.edu.njupt.tanksms.util.HttpUtils;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGE;
import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;
import static cn.edu.njupt.tanksms.util.LogUtils.LOGW;

/**
 * Created by DEEP on 2015/1/31.
 */
public class GetDetailTask extends BaseTask {


    private static final String TAG = "GetDetailTask";
    private final MainListItem item;

    public GetDetailTask(Context context, Serializable item) {
        super(context);
        this.item = (MainListItem) item;
        //LOGW(TAG,this.item.toString());
    }

    @Override
    public void run() {

        final String[] result_text = new String[2];

        HttpUtils http = new HttpUtils(Config.URL+Config.URL_DETAILS,context, new HttpUtils.HttpCallBack() {
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
        http.addHeader("Content-Type", "text/html");

        http.addParam("eid", item.getEid());
        http.addParam("ename", item.getEname());
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
        //LOGE(TAG,before + middle + after);
        return before + middle + after;
        //return data ;
    }

    private void setBroadcast(String[] result_text) {
        Intent intent = new Intent();
        intent.setAction(Config.DETAIL_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_GET_DETAILS);
        if(result_text[0].equals(Config.SUCCESS)){
            intent.putExtra(Config.TASK_FLAG,result_text[0]);
            intent.putExtra(Config.TASK_DATA, handlePage(result_text[1]));
            //intent.putExtra(Config.TASK_DATA, result_text[1]);

        }else{
            intent.putExtra(Config.TASK_FLAG,Config.FAILURE);
            intent.putExtra(Config.TASK_DATA,result_text[0]);
        }

        context.sendBroadcast(intent);
    }
}

package cn.edu.njupt.tanksms.task;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.model.MainListItem;
import cn.edu.njupt.tanksms.util.HttpUtils;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;


public class GetCurrentTask extends BaseTask {


    private static final String TAG = "GetCurrentTask";
    private ArrayList<MainListItem> list;

    public GetCurrentTask(Context context) {
        super(context);
    }

    @Override
    public void run() {

        final String[] result_text = new String[2];
        LOGI(TAG,"doInBackground==>"+" thread id: " + Thread.currentThread().getId() + " thread name: " + Thread.currentThread().getName());

        HttpUtils http = new HttpUtils(Config.URL+Config.URL_GET_CURRENT,context,new HttpUtils.HttpCallBack(){

            @Override
            public void onSuccess(int responseCode , String result , HttpURLConnection conn) {
                LOGI(TAG,"responseCode=>"+responseCode+"result=>"+result);
                if(responseCode == 200){
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String data = jsonObject.optString("data");
                        if(data == null){
                            result_text[0] = jsonObject.getString("error");
                        }else{
                            result_text[0] = Config.SUCCESS ;
                            //result_text[1] = data ;
                            handleJSON(data);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        result_text[0] = context.getString(R.string.require_data_error) ;
                    }
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

        http.get();

        sendBroadcast(result_text,list);

    }

    protected void sendBroadcast(String[] result_text, ArrayList<MainListItem> list) {
        Intent intent = new Intent();
        intent.setAction(Config.MAIN_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_GET_CURRENT);
        if(result_text[0].equals(Config.SUCCESS)){
            intent.putExtra(Config.TASK_FLAG,result_text[0]);
            intent.putExtra(Config.TASK_DATA, list);

        }else{
            intent.putExtra(Config.TASK_FLAG,Config.FAILURE);
            intent.putExtra(Config.TASK_DATA,result_text[0]);
        }

        context.sendBroadcast(intent);
    }

    private ArrayList<MainListItem> handleJSON(String json_string) throws JSONException {
        list = new ArrayList<MainListItem>();
        JSONArray json = new JSONArray(json_string);
        for(int i=0 ; i<json.length() ; i++){

            JSONObject vo = json.getJSONObject(i) ;
            list.add(new MainListItem(vo.getInt("eid"),vo.getString("ename"),vo.getInt("water_temp"),vo.getInt("water_gage"),vo.getInt("water_level")));

        }
        return list ;
    }

}

package cn.edu.njupt.tanksms.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.util.AccountHelper;
import cn.edu.njupt.tanksms.util.HttpUtils;
import cn.edu.njupt.tanksms.util.MD5Utils;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;


public class LoginTask extends BaseTask{

    private static final String TAG = "GetCurrentTask";

    private int uid ;               //返回的uid，值和username是一样的
    private String username ;       //实际是需要提交的uid
    private String uname ;          //真的uname
    private String password ;
    private String remark;

    public LoginTask(Context context,String username,String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {

        final String[] result_text = new String[2];
        LOGI(TAG,"doInBackground==>"+" thread id: " + Thread.currentThread().getId() + " thread name: " + Thread.currentThread().getName());

        HttpUtils http = new HttpUtils(Config.URL+Config.URL_LOGIN,context,new HttpUtils.HttpCallBack(){

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int responseCode , String result , HttpURLConnection conn) {
                if(responseCode == 200){
                    try {
                        LOGI(TAG,result);
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject success = jsonObject.optJSONObject("success");
                        if(success == null){
                            result_text[0] = jsonObject.getString("error");
                        }else{
                            uid = success.getInt("uid") ;
                            uname = success.getString("uname");
                            remark = success.getString("remark");
                            result_text[0] = Config.SUCCESS ;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        result_text[0] = context.getString(R.string.require_data_error) ;
                    }
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

        String code = MD5Utils.randomCode(10);

        http.cleanCookie();

        http.addParam("username", username);
        http.addParam("password", MD5Utils.hex_salt(MD5Utils.hex(MD5Utils.hex(password)),code));
        http.addParam("checkcode",code );
        http.addParam("remember",1+"");

        http.post();

        new AccountHelper(context).saveUserData(uid,uname,remark);

        sendBroadcast(result_text);

    }



    protected void sendBroadcast(String[] result_text) {
        Intent intent = new Intent();
        intent.setAction(Config.LOGIN_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_LOGIN);
        if(result_text[0].equals(Config.SUCCESS)){
            intent.putExtra(Config.TASK_FLAG,result_text[0]);
        }else{
            intent.putExtra(Config.TASK_FLAG,Config.FAILURE);
            intent.putExtra(Config.TASK_DATA,result_text[0]);
        }

        context.sendBroadcast(intent);
    }
}

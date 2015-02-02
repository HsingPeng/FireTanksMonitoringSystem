package cn.edu.njupt.tanksms.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.widget.MySnackBar;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGE;

/**
 * HttpUtils网络请求
 */
public class HttpUtils {

    private static final String TAG = "HttpUtils";
    private String uri;
    private Context context;
    private HttpCallBack callback;
    private HttpURLConnection connection;
    private InputStreamReader in;
    private String result;
    private String params = "";
    private Map<String,String> headerData = new HashMap<String,String>();

    public HttpUtils(String uri , Context context , HttpCallBack callback ){
        this.uri = uri ;
        this.context = context ;
        this.callback = callback ;
    }

    public void get(){
        if(!NetStatus()){
            return ;
        }
        callback.onStart();
        try {
            URL url = new URL(params.equals("")?uri:(uri+="?"+params));
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Config.CONNECT_TIME_OUT);//连接超时 单位毫秒
            connection.setReadTimeout(Config.CONNECT_READ_TIME_OUT);//读取超时 单位毫秒
            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            putCookie();
            for(String key : headerData.keySet()){
                connection.setRequestProperty(key,headerData.get(key));
            }

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuilder strBuffer = new StringBuilder();
            String line ;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();

            setCookie();
            handleResult();

        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(e,null);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void post(){
        if(!NetStatus()){
            return ;
        }
        callback.onStart();
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Config.CONNECT_TIME_OUT);//连接超时 单位毫秒
            connection.setReadTimeout(Config.CONNECT_READ_TIME_OUT);//读取超时 单位毫秒
            connection.setInstanceFollowRedirects(false);           //不响应跳转
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            putCookie();
            for(String key : headerData.keySet()){
                connection.setRequestProperty(key,headerData.get(key));
            }
            DataOutputStream dop = new DataOutputStream(
                    connection.getOutputStream());
            dop.writeBytes(params);
            dop.flush();
            dop.close();

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuilder strBuffer = new StringBuilder();
            String line ;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();

            setCookie();
            handleResult();

        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(e,null);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 添加请求参数
     * GET POST随请求变换
     * @param key
     * @param value
     */
    public void addParam(String key, String value){
        try {
            if(params.equals("")){
                    params += URLEncoder.encode(key,"utf-8") + "=" +  URLEncoder.encode(value,"utf-8") ;
            }else{
                params += "&" + URLEncoder.encode(key,"utf-8") + "=" + URLEncoder.encode(value,"utf-8") ;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加请求参数
     * GET POST随请求变换
     * @param key
     * @param value
     */
    public void addParam(String key,int value){
        if(params.equals("")){
            params += key + "=" +  value ;
        }else{
            params += "&" + key + "=" + value ;
        }
    }

    public void addHeader(String key,String value){
        headerData.put(key,value);
    }

    /**
     * 得到params
     * @return
     */
    public HttpURLConnection getConnection(){
        return connection;
    }

    /**
     * 设置请求位置
     * @param uri
     */
    public void setURI(String uri){
        this.uri = uri;
    }

    /**
     * 监测联网状态
     * @return boolean
     */
    public boolean isNetworkConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 清除Cookie
     */
    public void cleanCookie(){
        SharedPreferences sharedPref = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Cookie","");
        editor.commit();
    }

    private boolean NetStatus() {
        boolean flag = false ;
        if(isNetworkConnected()){
               flag = true;
        }else{
            callback.onFailure(null,context.getString(R.string.cannot_connect_to_the_network));
        }
        return flag ;
    }


    private void setCookie() {
        if (connection != null) {
            String key ;
            for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
                if (key.equalsIgnoreCase("set-cookie")) {
                    String sessionId = connection.getHeaderField(key);
                    sessionId = sessionId.substring(0, sessionId.indexOf(";"));
                    SharedPreferences sharedPref = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Cookie",sessionId);
                    editor.commit();
                }
            }
        }
    }


    private void putCookie() {
        SharedPreferences sharedPref = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        String cookie = sharedPref.getString("Cookie","");
        if(!cookie.equals("")){
            connection.setRequestProperty("Cookie",cookie);
        }
    }

    private void handleResult() throws IOException {
        int code = connection.getResponseCode() ;
        callback.onSuccess(code, decodeUnicode(result),connection);
        //callback.onSuccess(code, result,connection);
    }

    /**
     * unicode转为中文
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();

    }


    /**
     * HttpCallBack回调类
     */
    public static abstract class HttpCallBack{
        public HttpCallBack(){
        }

        public void onStart(){

        };

        public abstract void onSuccess(int responseCode, String result, HttpURLConnection conn);

        public abstract void onFailure(Exception e,String msg);

    }

    /**
     * 监测联网状态
     * @param context
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}

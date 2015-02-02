package cn.edu.njupt.tanksms.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import cn.edu.njupt.tanksms.Config;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGE;

public class AccountHelper {

    private static final String TAG = "AccountHelper";
    private final Context context;

    public AccountHelper(Context context) {
        this.context = context ;
    }

    /**
     * 保存用户数据
     * @param uid
     * @param uname
     * @param remark
     */
    public void saveUserData(int uid,String uname,String remark) {
        SharedPreferences sharedPref = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Config.UID,uid);
        editor.putString(Config.UNAME,uname);
        editor.putString(Config.REMARK,remark);
        editor.commit();
    }

    /**
     * 清除用户数据
     */
    public void cleanUserData(){
        SharedPreferences sharedPref = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Config.UID,-1);
        editor.putString(Config.UNAME,"");
        editor.putString(Config.REMARK,"");
        editor.commit();
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

    public void syncCookie(){
        SharedPreferences sharedPref = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        String cookies = sharedPref.getString("Cookie","");
        int position = Config.URL.indexOf("/",7);
        String domain = Config.URL.substring(0,position);
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(domain, cookies+"; path=/");//cookies是在HttpClient中获得的cookie
        LOGE(TAG, cookieManager.getCookie(domain));
        CookieSyncManager.getInstance().sync();
    }

}

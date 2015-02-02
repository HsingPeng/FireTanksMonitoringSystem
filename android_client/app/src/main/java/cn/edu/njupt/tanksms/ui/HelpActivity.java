package cn.edu.njupt.tanksms.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.service.MyService;
import cn.edu.njupt.tanksms.util.AccountHelper;
import cn.edu.njupt.tanksms.widget.MyDialog;
import cn.edu.njupt.tanksms.widget.MySnackBar;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGW;

/**
 * Created by DEEP on 2015/1/30.
 */
public class HelpActivity extends BaseActivity {

    private static final String TAG = "HelpActivity";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setView(int res) {
        super.setView(res);
        ImageView back = (ImageView)findViewById(R.id.imageView_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        setWebView();

        sendTask();

    }

    private void sendTask() {
        this.showDialog();
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_GET_HELP);
        startService(intent);
    }

    private void setWebView() {
        webView = (WebView)findViewById(R.id.webView_help);
        WebSettings setting = webView.getSettings();
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setJavaScriptEnabled(true);
        setting.setDisplayZoomControls(false);
        /*setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(Config.IS_DEBUG);
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LOGW(TAG,"shouldOverrideUrlLoading=>"+url);
                //return super.shouldOverrideUrlLoading(view, url);
                return true ;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                HelpActivity.this.dismissDialog();
            }
        });

        WebChromeClient client = new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                MyDialog dialog = new MyDialog(HelpActivity.this,"",message);
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result.confirm();
                    }
                });
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                MyDialog dialog = new MyDialog(HelpActivity.this,"",message);
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result.confirm();
                    }
                });
                dialog.addCancelButton(HelpActivity.this.getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result.cancel();
                    }
                });
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        };

        webView.setWebChromeClient(client);

        new AccountHelper(this).syncCookie();

    }

    @Override
    protected String[] onReceive(Context context, Intent intent) {

        int action = intent.getIntExtra(Config.ACTION_FLAG, Config.ACTION_DEFAULT);
        switch(action){

            case Config.ACTION_GET_HELP :
                //this.dismissDialog();
                handleGetHelp(intent);
                break;
        }

        return new String[]{Config.HELP_ACTIVITY_RECEIVER};
    }

    private void handleGetHelp(Intent intent) {
        String flag = intent.getStringExtra(Config.TASK_FLAG);

        if(flag.equals(Config.SUCCESS)){
            String data = intent.getStringExtra(Config.TASK_DATA);
            showData(data);
        }else {
            this.dismissDialog();
            String data = intent.getStringExtra(Config.TASK_DATA);
            if (data.equals(Config.CODE_302)) {
                logout();
            } else {
                new MySnackBar(this, data).show();
            }
        }
    }

    private void showData(String data) {
        webView.loadDataWithBaseURL(Config.URL,data,"text/html", "UTF-8", null);
    }

    private void logout() {
        Intent intent = new Intent(Config.MAIN_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_LOGOUT);
        this.sendBroadcast(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);

        }
    }

    private void back() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

}

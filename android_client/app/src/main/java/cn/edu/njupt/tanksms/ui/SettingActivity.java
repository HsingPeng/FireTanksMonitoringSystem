package cn.edu.njupt.tanksms.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;

public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";
    private MaterialEditText t_server_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    @Override
    protected String[] onReceive(Context context, Intent intent) {
        return null;
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
        t_server_address = (MaterialEditText)findViewById(R.id.t_server_address);
    }

    private void back() {
        handleResult();
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
        finish();
    }

    /**
     * 保存结果
     */
    private void handleResult() {
        save_server_address(t_server_address.getText().toString());
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

    /**
     * 初始化数据
     */
    private void initData(){
        t_server_address.setText(get_server_address());
    }

    private String get_server_address(){
        SharedPreferences sharedPref = this.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPref.getString(Config.SERVER_ADDRESS,Config.URL);
    }

    private void save_server_address(String adress){
        SharedPreferences sharedPref = this.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Config.SERVER_ADDRESS,adress);
        editor.apply();
    }

}

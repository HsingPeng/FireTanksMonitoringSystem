package cn.edu.njupt.tanksms.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.service.MyService;
import cn.edu.njupt.tanksms.widget.MyRectangleButton;
import cn.edu.njupt.tanksms.widget.MySnackBar;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private MaterialEditText t_username;
    private MaterialEditText t_password;
    private MyRectangleButton b_login;
    private boolean EXIT_FLAG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_login);
        initView();
        EXIT_FLAG = true ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EXIT_FLAG){
            Intent intent = new Intent(this,MyService.class);
            intent.putExtra(Config.ACTION_FLAG,Config.ACTION_STOP_SERVICE);
            startService(intent);
        }
    }

    @Override
    protected String[] onReceive(Context context, Intent intent) {

        LOGI(TAG,"onReceive()");
        int action = intent.getIntExtra(Config.ACTION_FLAG, Config.ACTION_DEFAULT);
        switch(action){

            case Config.ACTION_LOGIN :
               handleLogin(intent);
                break;

        }

        return new String[]{Config.LOGIN_ACTIVITY_RECEIVER};
    }

    /**
     * 处理结果
     * @param intent
     */
    private void handleLogin(Intent intent){
        String flag = intent.getStringExtra(Config.TASK_FLAG);

        if(flag.equals(Config.SUCCESS)){
            EXIT_FLAG = false ;
            Intent intent1 = new Intent(this, MainActivity.class);
            this.startActivity(intent1);
            this.finish();
        }else {
            new MySnackBar(this,intent.getStringExtra(Config.TASK_DATA)).show();
        }

        this.dismissDialog();

    }

    private void initView(){
        t_username = (MaterialEditText)findViewById(R.id.t_username);
        t_password = (MaterialEditText)findViewById(R.id.t_password);
        b_login = (MyRectangleButton)findViewById(R.id.b_login);
        b_login.setOnClickListener(new View.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                                LoginActivity.this.onClick();
                                       }
                                   }
        );
    }

    private void onClick() {
        boolean cancel = false;

        if(TextUtils.isEmpty(t_username.getText())){
            t_username.setError(getString(R.string.admin_id_not_blank));
            cancel = true ;
        }
        if(TextUtils.isEmpty(t_password.getText())){
            t_password.setError(getString(R.string.password_not_blank));
            cancel = true ;
        }

        if(!cancel){
            login();
        }
    }

    /**
     * 发送登陆请求的broadcast给service
     */
    private void login() {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_LOGIN);
        intent.putExtra(Config.USERNAME,t_username.getText().toString().trim());
        intent.putExtra(Config.PASSWORD,t_password.getText().toString().trim());
        startService(intent);
        this.showDialog();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingActivity.class) ;
            startActivity(intent);
            return true;
        }else if(id == R.id.action_exit){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    LoginActivity.this.finish();
                }
            }, 50);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

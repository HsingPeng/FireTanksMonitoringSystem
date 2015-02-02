package cn.edu.njupt.tanksms.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.io.Serializable;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.service.MyService;
import cn.edu.njupt.tanksms.widget.MyDialog;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";
    private TextView app_welcome_name;
    //开场动画是否结束
    public boolean anim_end = false ;
    //当开场动画未结束时将任务传入此，等待动画结束运行
    public Intent intent;
    private boolean EXIT_FLAG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        app_welcome_name = (TextView)findViewById(R.id.app_welcome_name);

        startTask();

        animation();

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

    /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);

        }
    }*/

    //发送任务
    private void startTask() {
        initServerAddress();
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_WELCOME);
        startService(intent);
    }

    private void initServerAddress() {
        SharedPreferences sharedPre = this.getSharedPreferences(Config.SHARED_PREF,Context.MODE_PRIVATE);
        String address = sharedPre.getString(Config.SERVER_ADDRESS,Config.URL);
        if(!address.equals("")){
            Config.URL = address ;
        }
    }

    @Override
    protected String[] onReceive(Context context, Intent intent) {
        LOGI(TAG,"onReceive()");
        int action = intent.getIntExtra(Config.ACTION_FLAG, Config.ACTION_DEFAULT);
        switch(action){

            case Config.ACTION_WELCOME :
                if(anim_end){
                    handleWelcome(intent);
                }else{
                    WelcomeActivity.this.intent = intent ;
                }
                break;

        }

        return new String[]{Config.WELCOME_ACTIVITY_RECEIVER};

    }

    private void handleWelcome(Intent intent) {
        String flag = intent.getStringExtra(Config.TASK_FLAG);

        if(flag.equals(Config.SUCCESS)){
            goMainActivity(intent.getSerializableExtra(Config.TASK_DATA));
        }else {

            String data = intent.getStringExtra(Config.TASK_DATA);

            //302即为未登录,此处省略进一步判断Location是否是登陆页面
            if(data.equals(Config.CODE_302)){
                goLoginActivity();
            }else{

                final MyDialog dialog = new MyDialog(WelcomeActivity.this,getString(R.string.connect_error),data);
                dialog.addCancelButton(getString(R.string.exit));
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WelcomeActivity.this.startTask();
                        dialog.accept = true;
                        Intent intent = new Intent(WelcomeActivity.this,MyService.class);
                        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_STOP_SERVICE);
                        startService(intent);
                    }
                });
                dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.accept = false;
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog_0) {
                        if(!dialog.accept){
                            WelcomeActivity.this.finish();
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                dialog.getButtonAccept().setText(getString(R.string.retry));

            }
        }

    }

    private void goLoginActivity() {
        EXIT_FLAG = false ;
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goMainActivity(Serializable data) {
        EXIT_FLAG = false ;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Config.TASK_DATA,data);
        startActivity(intent);
        finish();
    }

    private void animation() {

        AnimationSet anim=new AnimationSet(true);
        anim.setInterpolator(new DecelerateInterpolator());
        AlphaAnimation aa=new AlphaAnimation(0f,1f);
        TranslateAnimation ta=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_PARENT,0.6f,Animation.RELATIVE_TO_SELF,0f);
        ScaleAnimation sa = new ScaleAnimation(0.6f,1,0.6f,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.addAnimation(aa);
        anim.addAnimation(ta);
        anim.addAnimation(sa);
        anim.setDuration(1500);
        anim.setStartOffset(200);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                anim_end = true ;

                if(intent != null){
                    handleWelcome(intent);
                }
                    AlphaAnimation aa_repeat=new AlphaAnimation(1f,0.5f);
                    aa_repeat.setRepeatMode(Animation.REVERSE);
                    aa_repeat.setRepeatCount(Animation.INFINITE);
                    aa_repeat.setDuration(800);
                    app_welcome_name.startAnimation(aa_repeat);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        app_welcome_name.startAnimation(anim);

    }

}

package cn.edu.njupt.tanksms.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.adapter.MainListAdapter;
import cn.edu.njupt.tanksms.model.MainListItem;
import cn.edu.njupt.tanksms.service.MyService;
import cn.edu.njupt.tanksms.util.AccountHelper;
import cn.edu.njupt.tanksms.widget.MyDialog;
import cn.edu.njupt.tanksms.widget.MySnackBar;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;
import static cn.edu.njupt.tanksms.util.LogUtils.LOGW;

/**
 * Created by DEEP on 2015/1/24.
 */
public class MainActivity extends BaseActivity {

    private ListView list;
    private List<MainListItem> listData = new ArrayList<MainListItem>() ;
    private String TAG = "MainActivity";
    private MainListAdapter adapter;
    private boolean EXIT_FLAG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTAG(TAG);
        EXIT_FLAG = true ;
        setView(R.layout.activity_main);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        int action = intent.getIntExtra(Config.ACTION_FLAG,Config.ACTION_DEFAULT);
        switch (action){
            case Config.ACTION_GET_CURRENT :
                    handleGetCurrent(intent);
                break;
            case Config.ACTION_LOGOUT :
                logout();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LOGW(TAG, "onDestroy()");
        if(EXIT_FLAG){
            Intent intent = new Intent(this,MyService.class);
            intent.putExtra(Config.ACTION_FLAG,Config.ACTION_STOP_SERVICE);
            startService(intent);
        }
    }

    @Override
    protected void setView(int res) {
        super.setView(res);
        list = (ListView)findViewById(R.id.MainlistView);
        adapter = new MainListAdapter(this,listData);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goDetailActivity(position);
            }
        });
    }

    @Override
    protected String[] onReceive(Context context, Intent intent) {

        LOGI(TAG,"onReceive()");
        int action = intent.getIntExtra(Config.ACTION_FLAG, Config.ACTION_DEFAULT);
        switch(action){

            case Config.ACTION_GET_CURRENT :
                if(isForeground()){
                    handleGetCurrent(intent);
                }
                break;
            case Config.ACTION_LOGOUT :
                logout();
                break;
        }

        return new String[]{Config.MAIN_ACTIVITY_RECEIVER};
    }

    private void handleGetCurrent(Intent intent) {
        String flag = intent.getStringExtra(Config.TASK_FLAG);

        if(flag.equals(Config.SUCCESS)){
            ArrayList<MainListItem> item_list = (ArrayList<MainListItem>) intent.getSerializableExtra(Config.TASK_DATA);
            listData.clear();
            listData.addAll(item_list);
            adapter.notifyDataSetChanged();
        }else {
            String data = intent.getStringExtra(Config.TASK_DATA);
            if (data.equals(Config.CODE_302)) {
                    logout();
            } else {
                new MySnackBar(this, data).show();
            }
        }

        nextTask();

    }

    private void logout() {
        AccountHelper helper = new AccountHelper(this);
        helper.cleanCookie();
        helper.cleanUserData();
        this.EXIT_FLAG= false ;
        this.dismissDialog();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //创建定时任务
    private void nextTask() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(!MainActivity.this.isDestroyed()){
                    sendTask();
                }
            }
        },Config.REFRESH_PERIOD);
    }

    private void sendTask() {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_GET_CURRENT);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);

        }
    }

    private void exit() {
        final MyDialog exit_dialog = new MyDialog(this,getString(R.string.exit),getString(R.string.exit_the_app));
        exit_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(exit_dialog.accept){
                    MainActivity.this.finish();
                }
            }
        });
        exit_dialog.addCancelButton(getString(R.string.cancel));
        exit_dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit_dialog.accept = false;
            }
        });
        exit_dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit_dialog.accept = true;
            }
        });
        exit_dialog.setCancelable(true);
        exit_dialog.show();
        exit_dialog.getButtonAccept().setText(getString(R.string.accept));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log) {
            goLogActivity();
            return true;
        }if (id == R.id.action_settings) {
            goSettingActivity();
            return true;
        }else if(id == R.id.action_help){
            goHelpActivity();
            return true;
        }else if(id == R.id.action_logout){
            sendLogout();
        }else if(id == R.id.action_exit){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            },50);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendLogout() {
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_LOGOUT);
        startService(intent);
        this.showDialog();
    }

    private void goHelpActivity() {
        Intent intent = new Intent(this,HelpActivity.class) ;
        startActivity(intent);
    }

    private void goSettingActivity() {
        Intent intent = new Intent(this,SettingActivity.class) ;
        startActivity(intent);
    }

    private void goLogActivity() {
        startActivity(new Intent(this, LogActivity.class));
    }

    private void goDetailActivity(int position) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(Config.TASK_DATA,listData.get(position));
        startActivity(intent);
    }

}

package cn.edu.njupt.tanksms.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.task.GetCurrentTask;
import cn.edu.njupt.tanksms.task.GetDetailTask;
import cn.edu.njupt.tanksms.task.GetHelpTask;
import cn.edu.njupt.tanksms.task.GetLogTask;
import cn.edu.njupt.tanksms.task.LoginTask;
import cn.edu.njupt.tanksms.task.LogoutTask;
import cn.edu.njupt.tanksms.task.WelcomeTask;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;
import static cn.edu.njupt.tanksms.util.LogUtils.LOGW;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private ExecutorService threadPool;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LOGI(TAG,"onCreate()");
        threadPool = Executors.newFixedThreadPool(Config.THREAD_POOL_SIZE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(intent != null){
            int action = intent.getIntExtra(Config.ACTION_FLAG, Config.ACTION_DEFAULT);

            LOGI(TAG,"ACTION==>" + action);

            getAction(action,intent);


        }

        return START_NOT_STICKY ;
    }

    private void getAction(int action, Intent intent) {
        switch (action){
            case Config.ACTION_WELCOME:
                welcome();
                break;
            case Config.ACTION_LOGIN :
                login(intent);
                break;
            case Config.ACTION_GET_CURRENT :
                getCurrent();
                break;
            case Config.ACTION_GET_DETAILS :
                getDetails(intent);
                break;
            case Config.ACTION_GET_HELP :
                getHelp(intent);
                break;
            case Config.ACTION_GET_LOG :
                getLog(intent);
                break;
            case Config.ACTION_LOGOUT :
                logout();
                break;
            case Config.ACTION_STOP_SERVICE :
                stopSelf();
                break;
        }
    }

    private void logout() {
        threadPool.execute(new LogoutTask(this));
    }

    private void getLog(Intent intent) {
        threadPool.execute(new GetLogTask(this));
    }

    private void getHelp(Intent intent) {
        threadPool.execute(new GetHelpTask(this));
    }

    private void getDetails(Intent intent) {
        //LOGW(TAG,intent.getSerializableExtra(Config.TASK_DATA).toString());
        threadPool.execute(new GetDetailTask(this,intent.getSerializableExtra(Config.TASK_DATA)));
    }

    private void getCurrent() {
        threadPool.execute(new GetCurrentTask(MyService.this));
    }

    private void login(Intent intent) {
        threadPool.execute(new LoginTask(MyService.this,intent.getStringExtra(Config.USERNAME),intent.getStringExtra(Config.PASSWORD)));
    }

    private void welcome() {
        threadPool.execute(new WelcomeTask(MyService.this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGI(TAG,"onDestroy()");
        threadPool.shutdown();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}

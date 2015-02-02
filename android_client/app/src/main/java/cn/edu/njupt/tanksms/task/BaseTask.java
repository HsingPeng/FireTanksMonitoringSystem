package cn.edu.njupt.tanksms.task;

import android.content.Context;

import static cn.edu.njupt.tanksms.util.LogUtils.LOGI;

/**
 * Created by DEEP on 2015/1/25.
 */
public abstract class BaseTask implements Runnable {

    public Context context;

    public BaseTask(Context context){
        this.context = context ;
    }

}

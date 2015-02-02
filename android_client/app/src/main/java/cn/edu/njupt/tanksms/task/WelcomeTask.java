package cn.edu.njupt.tanksms.task;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.edu.njupt.tanksms.Config;
import cn.edu.njupt.tanksms.model.MainListItem;


public class WelcomeTask extends GetCurrentTask{

    public WelcomeTask(Context context) {
        super(context);
    }

    @Override
    protected void sendBroadcast(String[] result_text, ArrayList<MainListItem> list) {
        Intent intent = new Intent();
        intent.setAction(Config.WELCOME_ACTIVITY_RECEIVER);
        intent.putExtra(Config.ACTION_FLAG,Config.ACTION_WELCOME);
        if(result_text[0] == Config.SUCCESS){
            intent.putExtra(Config.TASK_FLAG,result_text[0]);
            intent.putExtra(Config.TASK_DATA,list);
        }else{
            intent.putExtra(Config.TASK_FLAG,Config.FAILURE);
            intent.putExtra(Config.TASK_DATA,result_text[0]);
        }

        context.sendBroadcast(intent);
    }
}

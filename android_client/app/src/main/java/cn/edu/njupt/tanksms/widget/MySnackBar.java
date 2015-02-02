package cn.edu.njupt.tanksms.widget;

import android.app.Activity;
import android.view.View;

import com.gc.materialdesign.widgets.SnackBar;

import cn.edu.njupt.tanksms.R;

/**
 * SnackBar类似于底部的toast通知
 */
public class MySnackBar extends SnackBar {

    private Activity activity;

    public MySnackBar(Activity activity, String text) {
        super(activity, text);
        this.activity = activity ;
    }

    public MySnackBar(Activity activity, String text, String buttonText, View.OnClickListener onClickListener) {
        super(activity, text, buttonText, onClickListener);
        this.activity = activity ;
    }

    @Override
    public void show() {
        super.show();
        this.setColorButton(activity.getResources().getColor(R.color.primary_color_green_light));
    }
}

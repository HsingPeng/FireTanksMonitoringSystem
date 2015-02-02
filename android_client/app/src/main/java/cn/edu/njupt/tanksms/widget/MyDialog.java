package cn.edu.njupt.tanksms.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.gc.materialdesign.widgets.Dialog;

import cn.edu.njupt.tanksms.R;

public class MyDialog extends Dialog {

    private final Context context;

    public boolean accept = false ;

    public MyDialog(Context context, String title, String message) {
        super(context, title, message);
        this.context = context ;
        context.setTheme(R.style.AppTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout backView = (RelativeLayout) findViewById(R.id.dialog_rootView);
        backView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void show() {
        super.show();
        this.getButtonAccept().setText(context.getString(R.string.accept));
        this.getButtonAccept().setBackgroundColor(context.getResources().getColor(R.color.primary_color_green_light));
    }

    @Override
    public void setOnAcceptButtonClickListener(View.OnClickListener onAcceptButtonClickListener) {
        super.setOnAcceptButtonClickListener(onAcceptButtonClickListener);
    }

    @Override
    public void setOnCancelButtonClickListener(View.OnClickListener onCancelButtonClickListener) {
        super.setOnCancelButtonClickListener(onCancelButtonClickListener);
    }
}

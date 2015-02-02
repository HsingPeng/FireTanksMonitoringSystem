package cn.edu.njupt.tanksms.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.gc.materialdesign.views.ButtonFlat;

import cn.edu.njupt.tanksms.R;

/**
 * Created by DEEP on 2015/1/27.
 */
public class MyFlatButton extends ButtonFlat{

    public MyFlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(getResources().getColor(R.color.primary_color_green_light));
    }
}

package cn.edu.njupt.tanksms.widget;

import cn.edu.njupt.tanksms.R;

public class MyRectangleButton extends com.gc.materialdesign.views.ButtonRectangle{


    public MyRectangleButton(android.content.Context context, android.util.AttributeSet attrs){

        super(context, attrs);
        this.setRippleSpeed(50);
        this.setBackgroundColor(context.getResources().getColor(R.color.primary_color_green_light));
    }


}

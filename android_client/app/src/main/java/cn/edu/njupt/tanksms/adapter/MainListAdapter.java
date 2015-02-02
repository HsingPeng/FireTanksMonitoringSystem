package cn.edu.njupt.tanksms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.njupt.tanksms.R;
import cn.edu.njupt.tanksms.model.MainListItem;

/**
 * Created by DEEP on 2015/1/28.
 */
public class MainListAdapter extends BaseAdapter {

    private final Context context;
    private List<MainListItem> items;

    public MainListAdapter(Context context ,List<MainListItem> items) {
        this.context = context ;
        this.items = items ;
    }

    @Override
    public int getCount() {
        return (items==null)?0:items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null ;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_main, null);
            holder = new ViewHolder() ;
            holder.ename = (TextView)convertView.findViewById(R.id.t_ename);
            holder.temp = (TextView)convertView.findViewById(R.id.t_temp);
            holder.gage = (TextView)convertView.findViewById(R.id.t_gage);
            holder.level = (TextView)convertView.findViewById(R.id.t_level);
            //holder.image = (ImageView)convertView.findViewById(R.id.imageView);
            holder.layout = (RelativeLayout)convertView.findViewById(R.id.list_layout);
            convertView.setTag(holder);     //绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();      //取出ViewHolder对象
        }

        holder.ename.setText(items.get(position).getEname()+context.getString(R.string.water_tank));

        //0->正常 1->警告 2->错误
        int color_flag = 0 ;

        holder.temp.setText(items.get(position).getTemp()+"℃");
        if(items.get(position).getTemp() < 4){
            color_flag = 2 ;
        }else if(items.get(position).getTemp() <=6){
            color_flag = 1 ;
        }

        String gage_text = context.getString(R.string.no_data) ;
        switch(items.get(position).getGage()){
            case 1:
                if(color_flag <2){
                    color_flag = 1 ;
                }
                gage_text = context.getString(R.string.water_gage_low) ;
                break;
            case 2:
                gage_text = context.getString(R.string.water_gage_normal) ;
                break;
            case 3:
                gage_text = context.getString(R.string.water_gage_normal) ;
                break;
        }
        holder.gage.setText(gage_text);

        String level_text = context.getString(R.string.no_data) ;
        switch(items.get(position).getTemp()){
            case 1:
                    color_flag = 2 ;
                level_text = context.getString(R.string.water_level_too_low) ;
                break;
            case 2:
                if(color_flag <2){
                    color_flag = 1 ;
                }
                level_text = context.getString(R.string.water_level_low) ;
                break;
            case 3:
            case 4:
                level_text = context.getString(R.string.water_level_normal) ;
                break;
        }
        holder.level.setText(gage_text);

        switch (color_flag){
            case 0:
                holder.layout.setBackgroundResource(R.drawable.listitem_main_bg_green);
                break;
            case 1:
                holder.layout.setBackgroundResource(R.drawable.listitem_main_bg_yellow);
                break;
            case 2:
                holder.layout.setBackgroundResource(R.drawable.listitem_main_bg_red);
                break;
        }

        //holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.table_icon));

        return convertView;
    }

    class ViewHolder{
        public TextView ename ;
        public TextView temp ;
        public TextView gage ;
        public TextView level ;
        public ImageView image ;
        public RelativeLayout layout ;


    }

}

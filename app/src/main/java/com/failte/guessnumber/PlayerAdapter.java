package com.failte.guessnumber;
//玩家Adapter 用于排行榜界面
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class PlayerAdapter extends BaseAdapter {
    Context context;
    ArrayList<GN_Player> list;

    public PlayerAdapter(Context context, ArrayList<GN_Player> list) {
        this.context = context;
        this.list = list;
    }

    //重写基础方法
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //重写getView方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //复用convertView，判断是否是首次加载，如若是，则加载，否则直接跳过，已减少运行时间
        if(convertView == null){
            viewHolder=new ViewHolder();
            //加载
            convertView= LayoutInflater.from(context).inflate(R.layout.gn_rank,parent,false);
            //使用类保存布局组件数据，以进行重用，减少反复进行寻找的时间
            viewHolder.rank_no=(TextView) convertView.findViewById(R.id.rank_no);
            viewHolder.rank_name = (TextView) convertView.findViewById(R.id.rank_name);
            viewHolder.rank_count = (TextView) convertView.findViewById(R.id.rank_count);
        }
        //重用ViewHolder
        else viewHolder=(ViewHolder) convertView.getTag();
        //设置界面显示的参数
        viewHolder.rank_no.setText(String.valueOf(position+1));
        viewHolder.rank_name.setText(list.get(position).getName());
        viewHolder.rank_count.setText(String.valueOf(list.get(position).getCount()));
        return convertView;
    }
}

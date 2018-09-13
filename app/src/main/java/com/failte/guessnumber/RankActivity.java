package com.failte.guessnumber;
//排行榜界面
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class RankActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        //实例化文件操作类FileOperation
        FileOperation fileOperation=new FileOperation();
        //读取排行数据
        final ArrayList<GN_Player> list=fileOperation.getRank(this);

        //定义适配器
        PlayerAdapter adapter=new PlayerAdapter(this, list);
        ListView listview=findViewById(R.id.rank_lv_showrank);

        //添加表头
        View listhead= LayoutInflater.from(this).inflate(R.layout.gn_rank_head,null,false);
        listview.addHeaderView(listhead);

        //将列表适配（Controller控制Model实现View）
        listview.setAdapter(adapter);

        //将列表添加点击事件
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //parent：官方解释为：The AdapterView where the click happened，也就是用户所点击的AdapterView，这个参数一般不用。
        //view：当前点击的列表项所对应的布局View对象，可通过这个参数获得相应的列表项内部的组件，进而对其进行操作。举个例子，假设有一个ListView，含有4个列表项，你点了第2个，那么通过view你就可以操作第2个列表项里面的TextView、ImageView等等的组件（假设存在）。
        //position：当前点击的列表项的位置，从0开始，也就是点击第n个，position就是n-1。
        //id：当前点击的列表项的序号，也是从0开始，一般情况下position和id是一样的。

        //表头不需要点击事件
        if(position!=0){
            String rank_name = (String) ((TextView)view.findViewById(R.id.rank_name)).getText();
            String rank_count = (String) ((TextView)view.findViewById(R.id.rank_count)).getText();
            Toast.makeText(this,rank_name+"用了"+rank_count+"次",Toast.LENGTH_SHORT).show();
        }
    }
}

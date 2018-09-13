package com.failte.guessnumber;
//主菜单界面
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //定义控件名称
    private ImageView main_gn_iv_title;
    private Button main_gn_bt_start;
    private Button main_gn_bt_continue;
    private Button main_gn_bt_rank;
    boolean isExit;
    long first;
    long second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化文件操作类FileOperation
        FileOperation fileOperation=new FileOperation();
        //判断程序是否是第一次运行，若不是则创建默认排行
        if(!fileOperation.existFile(this)) fileOperation.writeRank(this);

        //获取MainActivity的控件
        main_gn_iv_title=(ImageView)findViewById(R.id.main_gn_iv_title);
        main_gn_bt_start=(Button)findViewById(R.id.main_gn_bt_start);
        main_gn_bt_continue=(Button)findViewById(R.id.main_gn_bt_continue);
        main_gn_bt_rank=(Button)findViewById(R.id.main_gn_bt_rank);

        //为开始按键添加事件
        main_gn_bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始游戏，参数为false：重新
                Intent intent=new Intent(MainActivity.this,GNActivity.class);
                intent.putExtra("gamemode",false);
                startActivity(intent);
            }
        });
        //为继续按键添加事件，参数为true：继续
        main_gn_bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //继续游戏
                Intent intent=new Intent(MainActivity.this,GNActivity.class);
                intent.putExtra("gamemode",true);
                startActivity(intent);
            }
        });
        //为排行按键添加事件
        main_gn_bt_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //带list跳转到RankActivity
                Intent intent=new Intent(MainActivity.this,RankActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!isExit) {
            first=System.currentTimeMillis();
            isExit=true;
            Toast.makeText(this,"再按一次退出游戏",Toast.LENGTH_SHORT).show();
        }
        else {
            second=System.currentTimeMillis();
            if(second-first<1000) {
                System.exit(0);
            }
            else isExit=false;
        }
    }
}
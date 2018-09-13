package com.failte.guessnumber;
//结算界面
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BalanceActivity extends AppCompatActivity {

    TextView balance_tv_state;
    Button balance_bt_showrank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        balance_tv_state=findViewById(R.id.balance_tv_state);
        balance_bt_showrank=findViewById(R.id.balance_bt_showrank);

        //将intent获取的数据进行处理
        Intent getintent=getIntent();
        //对基本类型数据进行处理
        int state=getintent.getIntExtra("state",0);
        //对序列化对象进行处理
        GN_Player gnPlayer=(GN_Player) getintent.getSerializableExtra("gnPlayer");

        //根据获取到的state选择提示语
        switch (state) {
            case 0: balance_tv_state.setText("游戏结束！");break;
            case 1: balance_tv_state.setText("恭喜！"+gnPlayer.getName()+"本次用了"+gnPlayer.getCount()+"次");break;
            case 2: balance_tv_state.setText("新的记录！"+gnPlayer.getName()+"本次只用了"+gnPlayer.getCount()+"次");break;
        }

        //为展示排行按钮添加点击事件
        balance_bt_showrank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BalanceActivity.this,RankActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

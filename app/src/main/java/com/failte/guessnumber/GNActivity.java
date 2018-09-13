package com.failte.guessnumber;
//游戏界面
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class GNActivity extends Activity {

    private TextView main_gn_tv_maximum;
    private TextView main_gn_tv_result;
    private TextView main_gn_tv_minimum;
    private EditText main_gn_et_input;
    private Button main_gn_bt_submit;
    public boolean isExit;
    //实例化类以初始化取值范围和要猜的数值
    private GN_Game gn_game=new GN_Game();
    int tempcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gn);
        //绑定相关组件
        main_gn_tv_maximum=(TextView) findViewById(R.id.main_gn_tv_maximum);
        main_gn_tv_result=(TextView) findViewById(R.id.main_gn_tv_result);
        main_gn_tv_minimum=(TextView) findViewById(R.id.main_gn_tv_minimum);
        main_gn_et_input=(EditText)findViewById(R.id.main_gn_et_input);
        main_gn_bt_submit=(Button) findViewById(R.id.main_gn_bt_submit);

        //获取游戏模式
        Intent intent=getIntent();
        boolean gamemode=intent.getBooleanExtra("gamemode",false);
        //判断游戏模式
        if(gamemode){
            //继续游戏模式
            //读取存档
            gn_game.gn_fun_gameload(this);
            //读取异常检测
            tempcount=gn_game.loadCheck(this);
            //存档数据检测
            if(tempcount!=gn_game.getGn_int_count()) {
                Toast.makeText(this,"数据异常，游戏初始化",Toast.LENGTH_SHORT);
                gn_fun_gameStart();
            }
            else {
                //通过是否有猜过判断是否为有效存档，若有效则恢复，若无效则更改游戏模式为开始游戏
                if (gn_game.getGn_int_count() > 0) {
                    Toast.makeText(this, "恢复上次游戏状态", Toast.LENGTH_SHORT).show();
                    gn_fun_gamebackup();
                } else {
                    //无存档，重新开始
                    gn_fun_gameStart();
                }
            }
        }
        //开始游戏模式
        else gn_fun_gameStart();

        //为提交添加点击事件
        main_gn_bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //获取输入框的数值
                    String input_string = main_gn_et_input.getText().toString();
                    int input = Integer.parseInt(input_string);
                    //判断输入的字符是否是0开头的不推荐数字
                    if(!gn_game.gn_fun_startOfZero(input_string)){
                        Toast.makeText(GNActivity.this,"不推荐输入0开头的数字",Toast.LENGTH_SHORT).show();
                    }
                    //判断输入的数字是否在规定范围内，若为空或规定以外的数字则提示，每次提交输入内容都会将输入框情况
                    if (input > 0 && input < 100) {
                        //已猜次数+1，调用已猜数字方法
                        gn_game.addGn_int_count();
                        guessed();
                        //通过调用检测方法获取返回值，根据值进行反应
                        switch (gn_game.gn_fun_check(input)){
                            case 0:
                                //输入正确
                                Toast.makeText(GNActivity.this, "是的！你猜对了！是" + Integer.toString(input) + "!", Toast.LENGTH_SHORT).show();
                                gn_fun_gameOver();
                                break;
                            case 1:
                                //输入过大
                                main_gn_tv_maximum.setText(Integer.toString(gn_game.getGn_int_maximum()));
                                main_gn_et_input.setText(null);
                                Toast.makeText(GNActivity.this, Integer.toString(input)+"太大了，再猜一次吧~", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                //输入过小
                                main_gn_tv_minimum.setText(String.valueOf(gn_game.getGn_int_minimum()));
                                main_gn_et_input.setText(null);
                                Toast.makeText(GNActivity.this, Integer.toString(input)+"太小了，再猜一次吧~", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else {
                        //输入了限定输入数值以外的数值
                        main_gn_et_input.setText(null);
                        Toast.makeText(GNActivity.this, "输入值为有误哦~", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    //输入值转换成了非数字
                    main_gn_et_input.setText(null);
                    Toast.makeText(GNActivity.this, "请输入数值哦~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //双击退出
    @Override
    public void onBackPressed() {
        ensureExit();
    }

    //双击退出方法
    public void ensureExit(){
        Timer timer=null;
        if(!isExit){
            isExit=true;
            Toast.makeText(this,"再按一次返回上级菜单",Toast.LENGTH_SHORT).show();
            //若有进行过猜数字，则当前游戏为有效游戏局
            if(gn_game.getGn_int_count()>0) gn_game.gn_fun_gamestore(this,gn_game);
            //设置计划以取消退出
            timer=new Timer();
            timer.schedule(new TimerTask() {
                //重写计划的运行参数以取消退出
                @Override
                public void run() { isExit = false; }
                }, 1000);//schedule的首个参数为计划的运行进程，第二个参数为延时
            }
        else {
            Toast.makeText(this,"游戏已保存",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //游戏初始化方法
    private void gn_fun_gameInit(){
        //新建游戏对象，并初始化组件属性
        gn_game=new GN_Game();
        main_gn_tv_minimum.setText(Integer.toString(0));
        main_gn_tv_maximum.setText(Integer.toString(100));
        main_gn_et_input.setText(null);
    }

    //恢复游戏界面方法
    private void gn_fun_gamebackup(){
        //恢复组件属性
        main_gn_tv_minimum.setText(String.valueOf(gn_game.getGn_int_minimum()));
        main_gn_tv_maximum.setText(String.valueOf(gn_game.getGn_int_maximum()));
        main_gn_et_input.setText(null);
    }

    //开始游戏方法
    private void gn_fun_gameStart(){
        //初始化游戏
        gn_fun_gameInit();
        //调用本局游戏创建要猜的数字的方法
        gn_game.gn_fun_createNum();
        //初始化异常判断
        gn_game.savaCheck(this,tempcount=0);
    }

    //已猜数字调用方法
    public void guessed(){
        tempcount++;
        //保存异常判断
        gn_game.savaCheck(this,tempcount);
    }

    //正常结束游戏方法
    private void gn_fun_gameOver(){
        int state=1;
        //携带当前游戏的成功状态跳转到结算界面
        GN_Player gn_player=new GN_Player("你",gn_game.getGn_int_count());
        FileOperation fileOperation=new FileOperation();
        //存档异常检测系统
        gn_game.savaCheck(this,0);
        //判断是否进入排行
        if(fileOperation.inRank(this,gn_player.getCount())) {
            state=2;
            //更新排行榜
            fileOperation.updataRank(this,gn_player);
        }
        //更新完排行榜，清除存档
        gn_game.gn_fun_gamestore(this,new GN_Game());
        //带游戏完成状态与当前玩家的信息跳转到结算界面
        Intent intent=new Intent(this,BalanceActivity.class);
        intent.putExtra("state",state);
        intent.putExtra("gnPlayer",gn_player);
        startActivity(intent);
        //跳转后结束当前Activity
        finish();
//        System.exit(0);
    }

}

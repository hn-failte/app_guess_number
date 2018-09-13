package com.failte.guessnumber;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

//游戏类
public class GN_Game {

    //只能进行无参构造
    public GN_Game() {  }

    private int gn_int_minimum=0;
    private int gn_int_maximum=100;
    private int gn_int_result=0;
    private int gn_int_count=0;//本次游戏所猜的次数

    //定义量的getter和setter
    public int getGn_int_minimum() {
        return gn_int_minimum;
    }
    public void setGn_int_minimum(int gn_int_minimum) {
        this.gn_int_minimum = gn_int_minimum;
    }
    public int getGn_int_maximum() {
        return gn_int_maximum;
    }
    public void setGn_int_maximum(int gn_int_maximum) {
        this.gn_int_maximum = gn_int_maximum;
    }
    public int getGn_int_result() { return gn_int_result; }
    public int getGn_int_count() { return gn_int_count; }

    //count只能一次一次加
    public void addGn_int_count() {
        gn_int_count++;
    }

    //生成本局要猜的数字
    public boolean gn_fun_createNum(){
        while(gn_int_result==0){
            gn_int_result=(int)(Math.random()*100);
        }
        return gn_int_result==0&&gn_int_result>0&&gn_int_result<100;
    }

    //校对输入是否是以0开头的数字，若是返回不带0的数字
    public boolean gn_fun_startOfZero(String input){
        if(input.equals(String.valueOf(Integer.parseInt(input)))) return true;
        return false;
    }

    //校对输入结果是否正确
    public int gn_fun_check(int input){
        if (input==gn_int_result) {
            return 0;
        }
        else {
            if (input > gn_int_result) {
                gn_int_maximum = input;
                return 1;
            }
            else {
                gn_int_minimum = input;
                return 2;
            }
        }
    }

    //保存当前游戏状态
    public void gn_fun_gamestore(Context context, GN_Game gn_game){
        try {
            //参数设置为私密，可读写
            SharedPreferences.Editor editor=context.getSharedPreferences("state",Context.MODE_PRIVATE).edit();
            editor.putString("gn_int_minimum",String.valueOf(gn_game.getGn_int_minimum()));
            editor.putString("gn_int_maximum",String.valueOf(gn_game.getGn_int_maximum()));
            editor.putString("gn_int_result",String.valueOf(gn_game.getGn_int_result()));
            editor.putString("gn_int_count",String.valueOf(gn_game.getGn_int_count()));
            //提交，保存内容
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //读取上一次的游戏状态
    public void gn_fun_gameload(Context context){
        try {
            //参数设置为私密，可读写
            SharedPreferences sharedPreferences=context.getSharedPreferences("state",Context.MODE_PRIVATE);
            gn_int_minimum=Integer.valueOf(sharedPreferences.getString("gn_int_minimum","0"));
            gn_int_maximum=Integer.valueOf(sharedPreferences.getString("gn_int_maximum","0"));
            gn_int_result=Integer.valueOf(sharedPreferences.getString("gn_int_result","0"));
            gn_int_count=Integer.valueOf(sharedPreferences.getString("gn_int_count","0"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //保存异常判断
    public void savaCheck(Context context, int tempcount){
        try {
            //参数设置为私密，可读写
            SharedPreferences.Editor editor=context.getSharedPreferences("defence",Context.MODE_PRIVATE).edit();
            editor.putString("tempcount",String.valueOf(tempcount));
            //提交，保存内容
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //读取异常判断
    public int loadCheck(Context context){
        int tempcount=0;
        try {
            //参数设置为私密，可读写
            SharedPreferences sharedPreferences=context.getSharedPreferences("defence",Context.MODE_PRIVATE);
            tempcount=Integer.valueOf(sharedPreferences.getString("tempcount","0"));
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return tempcount;
        }
    }
}
package com.failte.guessnumber;
//玩家类
import java.io.Serializable;

public class GN_Player implements Serializable {
    //无参与有参构造
    public GN_Player() {
        super();
    }
    public GN_Player(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String name;
    public int count;

    //定义量的getter和setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    //重写toString
    @Override
    public String toString() {
        return "GN_Player{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}

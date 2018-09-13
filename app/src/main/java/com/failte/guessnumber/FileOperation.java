package com.failte.guessnumber;
//文件读写工具类
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileOperation {
    //检测文件是否存在
    public boolean existFile(Context context){
        boolean flag=false;
        try {
            //打开指定路径的文件
            FileInputStream fileInputStream = context.openFileInput("rank.txt");
            //文件能打开，文件存在
            flag=true;
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //返回文件检查结果
            return flag;
        }
    }

    //获取文件中的排行信息
    public ArrayList<GN_Player> getRank(Context context){
        GN_Player player=null;
        ArrayList<GN_Player> list=new ArrayList<GN_Player>();
        int i=0;
        try {
            FileInputStream fileInputStream=context.openFileInput("rank.txt");
            int temp;
            byte[] bytes=new byte[256];
            int g=0;
            //循环读取流，直到流返回为空的-1信息
            while((temp=fileInputStream.read())!=-1){
                //判断读取到的ASCLL是否为空的ASCLL
                if(temp!=32){
                    //将ASCLL码放入byte数组等待处理
                    bytes[g]=(byte) temp;
                    g++;
                }
                else {
                    //将byte数组转换成字符串
                    String string=new String(bytes,0,bytes.length);
                    //重置byte数组
                    bytes=new byte[1024];
                    g=0;
                    //通过控制参数，将字符数组转换的属性分别放入对象的不同属性
                    if(i==0){
                        //第一个属性，实例对象
                        player=new GN_Player();
                        //将转换的字符串格式化
                        player.setName(string.trim());
                        //第一个对象属性处理完毕，更改控制参数
                        i++;
                    }
                    else {
                        //对象第二个属性类型为int，将转换的字符串格式化为int并设置为对象的属性
                        player.setCount(temp=Integer.parseInt(string.trim()));
                        //将对象放入对象数组
                        list.add(player);
                        //此对象处理完毕，更改控制参数
                        i=0;
                    }
                }
            }
            fileInputStream.close();
        }catch (Exception e){
            //若报错将list设定为null
            list=null;
            e.printStackTrace();
        }
        finally {
            //使用finally返回处理的list
            return list;
        }
    }

    //创建排行文件
    public boolean writeRank(Context context){
        boolean flag=false;
        try {
            FileOutputStream fileOutputStream=context.openFileOutput("rank.txt",Context.MODE_APPEND);
            //定义默认排行文件内容
            String s="张三 3 李四 4 王五 5 赵六 6 神七 7 胡八 8 游九 9 楚十 10 萧十一 11 秦十二 12 ";
            //将字符串内容以ascll码的形式写入文件输出流
            fileOutputStream.write(s.getBytes());
            fileOutputStream.close();
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //返回是否创建成功
            return flag;
        }
    }

    //判断本次结算是否进入排行
    public boolean inRank(Context context, int count){
        ArrayList<GN_Player> list=this.getRank(context);
        for(int m=0;m<list.size();m++){
            if(count<list.get(m).getCount()) return true;
        }
        return false;
    }
    //更新排行文件
    public boolean updataRank(Context context, GN_Player gn_player){
        boolean flag=false;
        try {
            //定义默认排行文件内容
            StringBuffer sb=new StringBuffer();
            GN_Player player=null;
            ArrayList<GN_Player> list=getRank(context);
            //重新定义一个list
            ArrayList<GN_Player> updata_list=new ArrayList<GN_Player>();

            //从高名次往低名次查询，查询到后将对象插入，由于算法问题，此处保留状态，终止循环
            int temp_m=0;
            for(int m=0;m<list.size()-1;m++){
                if(gn_player.getCount()<list.get(m).getCount()) {
                    updata_list.add(gn_player);
                    temp_m=m;
                    break;
                }
                updata_list.add(list.get(m));
                temp_m=m;
            }
            //继续上次状态的循环
            for(int n=temp_m;n<list.size()-1;n++){
                updata_list.add(list.get(n));
            }
            //释放list
            list.clear();
            //将更新后的排行转换为字符串
            for(int i=0;i<updata_list.size();i++){
                sb.append(updata_list.get(i).getName()+" "+updata_list.get(i).getCount()+" ");
            }
            //将字符串内容以ascll码的形式写入文件输出流
            FileOutputStream fileOutputStream=context.openFileOutput("rank.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(sb.toString().getBytes());
            fileOutputStream.close();
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //返回是否创建成功
            return flag;
        }
    }
}

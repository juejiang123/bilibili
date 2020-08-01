package com.bilibili.Utils;

import java.util.Random;

public class RandomUtil {

    /**
     *随机生成指定长度字符串
     */
    public String getStrAndNumByLen(int len){
        final String chars = "1,2,3,4,5,6,7,8,9,0,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        String[] s = chars.split(",");
        int length = s.length;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        for(int i=0;i<len;i++){
            int i1 = r.nextInt(length);
            sb.append(s[i1]);
        }
        String s1 = sb.toString();
        return s1;
    }

    public String getStrByLen(int len){
        int count=0;
        final String str = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String[] split = str.split(",");
        int length = split.length;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        while (count<len){
            int i = r.nextInt(length);
            sb.append(split[i]);
            count++;
        }
        return sb.toString().toLowerCase();

    }

    public String getStrByZLen(int len){
        int count=0;
        final String str = "中,国,你,好";
        String[] split = str.split(",");
        int length = split.length;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        while (count<len){
            int i = r.nextInt(length);
            sb.append(split[i]);
            count++;
        }
        return sb.toString();

    }

    /**
     * 生成指定长度的数字，以int类型返回
     * @param len
     * @return
     */
    public int getRanIntByLen(int len){
        int count=0;
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (count<len){
            int i = r.nextInt(10);
            if(i==0 && count==0){
                //表示不生成0982的数字
            }else {
                sb.append(i);
                count++;
            }

        }
        int i = Integer.parseInt(sb.toString());
        return i;
    }

    /**
     * 生成指定范围内的整数
     * @param min
     * @param max
     * @return
     */
    public int getRanInt(int min,int max){
        Random r = new Random();
        //10,100 11-99的范围
//        int i = r.nextInt(max);//0-(max-1)
        int i= r.nextInt(max)%(max-min+1)+ min;
        return i;
    }

    /**
     * 生成指定范围内的浮点数
     * @param min
     * @param max
     * @return
     */
    public float randomFloat(int min,int max){
        Random r= new Random();
        float x = min;
        System.out.println(x);
        while (x<=min){
            double db = r.nextDouble()*max*100;
            System.out.println(db);
            x = ((int) db)/100f;
        }

        return x;
    }

    /**
     * 生成指定范围内的数字，不包含参数本身
     * @param extent
     * @return
     */
    public int getExtentRanNum(int extent){
        int num = (int) (Math.random()*extent);
        return num;
    }


    public static void main(String[] args) {
        RandomUtil ru = new RandomUtil();
//        String strAndNumByLen = ru.getStrAndNumByLen(10);
//        System.out.println(strAndNumByLen);
//        String strByLen = ru.getStrByLen(20);
//        System.out.println(strByLen);
//        String strByZLen = ru.getStrByZLen(5);
//        System.out.println(strByZLen);
//        int ranInt = ru.getRanInt(2, 6);
//        System.out.println(ranInt);
        int ranIntByLen = ru.getRanIntByLen(4);
        System.out.println(ranIntByLen);
        int extentRanNum = ru.getExtentRanNum(5);
        System.out.println(extentRanNum);
        float v = ru.randomFloat(10, 20);
        System.out.println(v);

    }
}

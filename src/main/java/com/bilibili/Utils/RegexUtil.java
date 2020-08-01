package com.bilibili.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     *
     * @param r
     * @param index 提取第几组数字，从0开始
     * @return 返回一个整数，如果返回-1则表明提取的组数不存在
     */
    public static int getInt(String r,int index){
        Pattern p = Pattern.compile("\\d{1,7}");//1指的连续数字的最少个数
        Matcher m = p.matcher(r);
        List<Integer> result = new ArrayList<Integer>();
        while (m.find()){
            result.add(Integer.valueOf(m.group()));
        }
        if(!result.isEmpty() && index <result.size()){
            return result.get(index);
        }else {
            System.out.println("你要找的第"+(index+1)+"组数字不存在");
            return -1;
        }

    }

    public static void main(String[] args) {
        System.out.println("appium -p 2233 -bp 4690 3JU4C18416001256 >null/Desktop/3JU4C184160012560.log\n");
        int anInt = getInt("appium -p 2233 -bp 4690 3JU4C1841 >null/Desktop/3JU4C184160012560.log", 0);
        System.out.println(anInt);

    }
}

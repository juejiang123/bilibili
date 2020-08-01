package com.bilibili.Utils;

import java.io.*;
import java.util.Properties;
import java.util.Set;

//读取配置文件工具
public class PropUtil {

    private String filePath;
    private Properties prop;

    public PropUtil(String filePath){
        this.filePath = filePath;
        this.prop =readFile();
    }

    /**
     * readProp，读取文件内容，并返回
     * @return
     */
    public Properties readFile() {
        Properties prop = new Properties();
        try {
            FileReader fr = new FileReader(filePath);
            prop.load(fr);
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 写入所有数据
     * @param prop
     */
    public void writeFile(Properties prop){
        try {
            OutputStream ops = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(ops,"utf-8");
            prop.store(osw,"写入数据");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回指定key的值
     * @param key
     * @return
     * @throws IOException
     */
    public String getProp(String key) throws IOException {
        if(prop.containsKey(key)){
            return prop.getProperty(key);
        }else {
            System.out.println("获取对象为空");
            return "";
        }
    }

    /**
     * 设置新的key,value
     * @param key
     * @param value
     */
    public void setProp(String key,String value) {
        if(prop == null){
            prop = new Properties();
        }
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
            prop.setProperty(key,value);
            prop.store(bw,key+": value");
            bw.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        PropUtil pu = new PropUtil("b.txt");
        prop.setProperty("111","height");
        prop.setProperty("222","name");
        prop.setProperty("333","sex");
        pu.writeFile(prop);
        String prop1 = pu.getProp("222");
        System.out.println(prop1);
    }
}

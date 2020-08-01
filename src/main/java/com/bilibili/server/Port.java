package com.bilibili.server;

import com.bilibili.Utils.DosCmd;
import com.bilibili.Utils.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public class Port {

    /**
     *
     * @param portNum
     * @return boolean
     */
    public  Boolean isPortUsed(int portNum){
        Boolean flag = true;
        String osName = System.getProperty("os.name");
        String command = null;
        if(osName.toLowerCase().contains("mac")){
            command = "netstat -an |grep "+portNum;
        }else if(osName.toLowerCase().contains("win")){
            command = "netstat -ano |findstr "+portNum;
        }
        List<String> list = new DosCmd().execCmdConsole(command);
        System.out.println(list.size());
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                int anInt = RegexUtil.getInt(list.get(i), 3);
                System.out.println(anInt);
                if(anInt==portNum){
                    System.out.println("端口被占用了");
                }else {
                    flag = false;
                }
            }
        }else {
            System.out.println("端口未占用");
            flag = false;
        }
        return flag;
    }

    public  List<Integer> generatePortList(int portStart,int count){
        List<Integer> portList = new ArrayList<Integer>();
        while (portList.size() !=count)
            if(portStart>0 && portStart<60000+portList.size()){
                if(!isPortUsed(portStart)){
                    portList.add(portStart);
                }
                portStart++;
            }
        return portList;
    }

    public static void main(String[] args) {
        Boolean portUsed = new Port().isPortUsed(2233);
        System.out.println(portUsed);
    }

}

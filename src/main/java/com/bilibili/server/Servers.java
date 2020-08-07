package com.bilibili.server;

import com.bilibili.Utils.DosCmd;

import java.util.ArrayList;
import java.util.List;

public class Servers {

    private DosCmd cmd;
    private Port port;
    private List<String> deviceList;
    private List<Integer> appiumPortList;
    private List<Integer> bootstrapList;
//    private String path = System.getProperty("user.ir");

    public Servers(DosCmd cmd,Port port){
        this.cmd = cmd;
        this.port = port;
    }

    /**
     *获取设备uuid
     * @return
     */
    public List<String> getDevices(){
        List<String> list = cmd.execCmdConsole("adb devices");
        List<String> devList = new ArrayList<String>();
//        System.out.println(list.size());
        if(list.size()>2){
            for(int i=1;i<list.size()-1;i++){
//                System.out.println(list.get(i));
                String[] s = list.get(i).split("\t");
                if(s[s.length-1].trim().toLowerCase().contains("device")) {
                    devList.add(s[0].trim());
//                    System.out.println(111);
                }else{
                    System.out.println("无正常连接的设备");
                }
            }
        }else {
            System.out.println("设备未连接");

        }
        return devList;
    }

    public List<Integer> getPortList(int start){
        List<Integer> list = port.generatePortList(start, getDevices().size());
        return list;

    }

    /**
     * 生成服务端命令 appium -p xxxx -bp xxxx -U xxxxx>Desktop/1.log
     * @return
     */
    public List<String> generateServerCommand(){
        List<String> list = new ArrayList<String>();
        deviceList = getDevices();
        appiumPortList = getPortList(2233);
        bootstrapList = getPortList(4690);
        for(int i=0;i<deviceList.size();i++){
            String command = "appium -p "+appiumPortList.get(i)+" -bp "+bootstrapList.get(i)+" -U "+deviceList.get(i);
            list.add(command);
        }
        return list;
    }

    /**
     * 启动appium服务端
     */

    public void startServer(){
        List<String> list = generateServerCommand();
        DosCmd cmd = new DosCmd();
        for(int i=0;i<list.size();i++){
            cmd.execCmd(list.get(i));
        }
    }


    public Boolean killServer(){
        String command = null;
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("mac")) {
            command = "killall node";
        }else if(osName.toLowerCase().contains("win")){
            command = "taskkill -F -PID node.exe";
        }else {
            command = "taskkill -F -PID node.exe";
        }
        if(new DosCmd().execCmd(command)){
            return false;
        }else {
            return true;
        }
    }


    public static void main(String[] args) {
        Servers servers = new Servers(new DosCmd(),new Port());
//        List<String> devices = servers.getDevices();
//        for (String dev : devices){
//            System.out.println(dev);
//
//        }
        List<String> list = servers.generateServerCommand();
        for(String li : list){
            System.out.println(li);
        }
        servers.killServer();
        servers.startServer();
    }
}

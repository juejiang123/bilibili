package com.bilibili.Utils;

import com.bilibili.server.Port;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DosCmd {
    Logger logger = Logger.getLogger(DosCmd.class);
    String osName = System.getProperty("os.name");
    /**
     * get result of command,after execute dos command
     * @param cmdString
     */
    public List<String> execCmdConsole(String cmdString) {
        List<String> list = new ArrayList<String>();
        try {
            Process process =null;
//            System.out.println(cmdString);
            if(osName.toLowerCase().contains("mac")){
                String[] command = {"/bin/sh","-c",cmdString};
                process = Runtime.getRuntime().exec(command);
            }else if (osName.toLowerCase().contains("win")){
                process = Runtime.getRuntime().exec("cmd /c"+cmdString);
            }
            InputStream inputStream = process.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bf.readLine())!= null){
                list.add(line);
            }
            process.waitFor();
            process.destroy();

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 执行命令，并检查是否成功
     * @param cmdString
     * @return
     */
    public boolean execCmd(String cmdString){
        Runtime runtime = Runtime.getRuntime();
        try {
            if(osName.toLowerCase().contains("mac")) {
                String[] command = {"/bin/sh","-c",cmdString};
                runtime.exec(command);
            }else if(osName.toLowerCase().contains("win")){
                runtime.exec("cmd/c" + cmdString);
            }
            int anInt = RegexUtil.getInt(cmdString, 0);
            if(anInt>0){
                Port port = new Port();
                while (!port.isPortUsed(anInt)){
                    Thread.sleep(1000);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }





    public static void main(String[] args) {
        DosCmd cmd = new DosCmd();
        List<String> list = cmd.execCmdConsole("adb devices");
        for(String l : list){
            System.out.println(l);
        }
        System.out.println(list.size());
    }

}

package com.bilibili.Utils;

import java.util.List;

public class InputGet {

    public static String getInputName(){
        List<String> list = new DosCmd().execCmdConsole("adb shell ime list -s");
        if(list.contains("com.baidu.input_huawei/.ImeService")){
            int i = list.indexOf("com.baidu.input_huawei/.ImeService");
            return list.get(i);
        }else {
            return "";
        }
    }
}

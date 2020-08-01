package com.bilibili.Utils;

import org.openqa.selenium.By;

import java.io.IOException;

public class GetByLocator {

    public static By getLocator(String key) throws IOException {
        PropUtil prop = new PropUtil("./src/main/resources/configs/Locator.properties");
            //locator = name>登录或者注册
            String prop1 = prop.getProp(key);
            String locatorType = prop1.split(">")[0];
            String locatorValue = prop1.split(">")[1];
            System.out.println("获取类型"+locatorType+"\t 获取定位方式"+ locatorValue);
            if(locatorType.toLowerCase().equals("id")){
                return By.id(locatorValue);
            }else if(locatorType.toLowerCase().equals("name")){
                return By.name(locatorValue);
            }else if(locatorType.toLowerCase().equals("className")){
                return By.className(locatorValue);
            }else if(locatorType.toLowerCase().equals("tagname")){
                return By.className(locatorValue);
            }else if(locatorType.toLowerCase().equals("linktext")){
                return By.linkText(locatorValue);
            }else if(locatorType.toLowerCase().equals("partiallinktext")){
                return By.partialLinkText(locatorValue);
            }else if(locatorType.toLowerCase().equals("cssselector")){
                return By.cssSelector(locatorValue);
            }else if(locatorType.toLowerCase().equals("xpath")){
                return By.xpath(locatorValue);
            }else if(locatorType.toLowerCase().equals("")) {
                return By.xpath(locatorValue);
            }else {
                try {
                    throw new Exception("输入的locator type 未在程序中找到");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
    }


}

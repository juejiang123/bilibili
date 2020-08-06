package com.bilibili.Pages;

import com.bilibili.base.AndroidDriverBase;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BasePage {
    public String curActivity;
    public String pageSource;
    public AndroidDriver<AndroidElement> driver;

    public BasePage(AndroidDriver<AndroidElement> driver){
        this.driver = driver;
        this.curActivity = getCurActivity();
        this.pageSource = getPageSource();
    }

    public String getCurActivity(){
        return driver.currentActivity();
    }

    public String getPageSource(){
        String pageSource = driver.getPageSource();
        return pageSource;
    }

    //输入
    public void sendKeys(AndroidElement element,String str){
        if(element != null){
            element.sendKeys(str);
        }else {
            System.out.println("元素未获取到");
        }

    }

    //直接定位并输入
    public void sendKeys(By by, String value){
        AndroidElement element = driver.findElement(by);
        sendKeys(element,value);
    }

    //点击
    public void click(AndroidElement element){
        if(element != null){
            element.click();
        }else {
            System.out.println("元素没有找到");
        }
    }

    //定位并点击
    public void click(By by){
        AndroidElement element = driver.findElement(by);
        click(element);
    }

    //点击一个点
    public  void clickByCoordinate(int x, int y){
        try{
            TouchAction action = new TouchAction(driver);
//            Duration duration = Duration.ofSeconds(2);
            action.tap(new PointOption().point(x+5,y+5)).release().perform();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //清除
    public static void clear(AndroidElement element){
        if(element != null){
            element.clear();
        }else {
            System.out.println("元素未获取到");
        }
    }

    public void clearOneByOne(AndroidElement element) throws InterruptedException {
        if(element != null){
            element.click();
            driver.pressKey(new KeyEvent(AndroidKey.MOVE_END));
            String text = element.getText();
            int num =0;
            for (int i=text.length();i>0;i--){
                driver.pressKey(new KeyEvent(AndroidKey.DEL));
                num++;
                System.out.println("执行第"+num+"次操作");
                Thread.sleep(1000);
            }

        }else {
            System.out.println("元素未获取到");
        }
    }

    //坐标元素点击，针对一些能定位到整体元素但具体元素无法定位且具有规律性的元素，获取每一个子元素的中心点坐标
    public static List<Point> getElementByCoordinates(AndroidElement element,int rows,int colums){
        List<Point> elementResolve = new ArrayList<>();
        if(element !=null){
            int startX = element.getLocation().getX();
            int startY = element.getLocation().getY();
            int height = element.getSize().getHeight();
            int width = element.getSize().getWidth();
            for(int i=0;i<rows;i++){
                for(int j=0;j<colums;j++){
                    int x = width/(2*colums)*(2*j+1)+startX;
                    int y = height/(2*rows)*(2*j+1)+startY;
                    Point p = new Point(x,y);
                    elementResolve.add(p);
                }
            }

        }
        return elementResolve;
    }

    //获取元素指定索引的值
    public  void clickElementByCoordinate(AndroidElement element,int rows,int colums,int index){
        if(element !=null){
            List<Point> elementByCoordinates = getElementByCoordinates(element, rows, colums);
            if(!elementByCoordinates.isEmpty() && elementByCoordinates !=null){
                clickByCoordinate(elementByCoordinates.get(index).x,elementByCoordinates.get(index).y);
            }else {
                System.out.println("集合为空");
            }
        }else{
            System.out.println("元素为空");
        }
    }






}

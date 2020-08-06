package com.bilibili.base;

import com.bilibili.Log.Log;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

public class SwipeScreen {

    AndroidDriverBase driver;
    TouchAction action;
    private Log logger = Log.getLogger(SwipeScreen.class);


    public SwipeScreen(AndroidDriverBase Androiddriver){
        this.driver = Androiddriver;
        this.action = new TouchAction(driver);
    }

    public void swipe(String direction,int duration){
        int appWidth = driver.getAppWidth();
        int appHeight = driver.getAppHeight();

        Duration duration1 = Duration.ofSeconds(duration);
        switch (direction){
            case "up":
                action.press(new PointOption().point(appWidth/2,appHeight*3/4)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(appWidth/2,appHeight/2)).release().perform();
                logger.debug("向上滑动");
                break;
            case "down":
                action.press(new PointOption().point(appWidth/2,appHeight/2)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(appWidth/2,appHeight*3/4)).release().perform();
                logger.debug("向下滑动");
                break;
            case "left":
                action.press(new PointOption().point(appWidth*3/4,appHeight/2)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(appWidth/2,appHeight/2)).release().perform();
                logger.debug("向左滑动");
                break;
            case "right":
                action.press(new PointOption().point(appWidth/2,appHeight/2)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(appWidth*3/4,appHeight/2)).release().perform();
                logger.debug("向右滑动");
                break;
            default:
                System.out.println("输入的方向非法");
                logger.debug("不滑动");
        }
    }
    public void swipeElement(AndroidElement element,String direction,int duration){
        int x = element.getCenter().x;
        int y = element.getCenter().y;
        int x1 = x-10;
        int y1 = y-10;
        Duration duration1 = Duration.ofSeconds(duration);
        switch(direction){
            case "up":
                action.press(new PointOption().point(x,y1)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(x,y)).release().perform();
                logger.debug("向上滑动");
                break;
            case "down":
                action.press(new PointOption().point(x,y)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(x,y1)).release().perform();
                logger.debug("向下滑动");
                break;
            case "left":
                action.press(new PointOption().point(x,y)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(x1,y)).release().perform();
                logger.debug("向左滑动");
                break;
            case "right":
                action.press(new PointOption().point(x1,y)).waitAction(WaitOptions.waitOptions(duration1)).moveTo(new PointOption().point(x,y)).release().perform();
                logger.debug("向右滑动");
                break;
            default:
                System.out.println("输入的方向参数不合法");

        }

    }
}

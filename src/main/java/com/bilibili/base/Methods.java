package com.bilibili.base;

import com.bilibili.Utils.GetByLocator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;

public class Methods {

    AndroidDriver<AndroidElement> driver;

    public Methods(AndroidDriver<AndroidElement> AndroidDriver) {
        driver = AndroidDriver;
    }

    public void bilibiliLogin() throws InterruptedException, IOException {
        AndroidElement agree = driver.findElement(GetByLocator.getLocator("agree"));
        agree.click();
//        AndroidElement disagree = driver.findElementByName("不同意");
//        disagree.click();
        Thread.sleep(2000);
        AndroidElement mine = driver.findElement(GetByLocator.getLocator("tab5"));
        mine.click();
        driver.findElement(GetByLocator.getLocator("tapToLogin")).click();
        AndroidElement code = null;
        try {
            code = driver.findElement(GetByLocator.getLocator("passwordLogin"));
            code.click();

        } catch (Exception e) {
            System.out.println(code + "未找到");
        }
        AndroidElement account = driver.findElement(GetByLocator.getLocator("account"));
        account.sendKeys("15110027095");
//        Utils.clear(driver,account);
//        account.sendKeys("15110027095");
        AndroidElement password = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"tv.danmaku.bili:id/userpwd\")");
        password.sendKeys("tqx12345");
//        Utils.clear(driver,password);
//        password.sendKeys("tqx12345");
        driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"tv.danmaku.bili:id/btn_login\")").click();

    }

}

package com.bilibili.base;

import com.bilibili.Pages.BasePage;
import com.bilibili.Utils.GetByLocator;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.IOException;

public class Login {
    AndroidDriver<AndroidElement> driver;

    public Login(AndroidDriver<AndroidElement> AndroidDriver) {
        driver = AndroidDriver;
    }

    public void bilibiliLogin(String userName,String password) throws InterruptedException, IOException {
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
        account.sendKeys(userName);
//        Utils.clear(driver,account);
//        account.sendKeys("15110027095");
        AndroidElement password1 = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"tv.danmaku.bili:id/userpwd\")");
        password1.sendKeys(password);
//        BasePage.clear(password1);
//        password1.sendKeys(password);
//        BasePage bp = new BasePage(driver);
//        bp.clearOneByOne(password1);
//        password1.sendKeys(password);
//        Utils.clear(driver,password);
//        password.sendKeys("tqx12345");
        driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"tv.danmaku.bili:id/btn_login\")").click();

    }

}

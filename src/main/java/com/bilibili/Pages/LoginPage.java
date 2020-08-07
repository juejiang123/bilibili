package com.bilibili.Pages;

import com.bilibili.Utils.GetByLocator;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.IOException;

public class LoginPage extends BasePage {

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public MinePage bilibiliLogin(String userName, String password) throws InterruptedException, IOException {
        AndroidElement agree = driver.findElement(GetByLocator.getLocator("agree"));
        if(agree != null){
            agree.click();
        }
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
        AndroidElement password1 = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"tv.danmaku.bili:id/userpwd\")");
        password1.sendKeys(password);
        driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"tv.danmaku.bili:id/btn_login\")").click();
        return (new MinePage(driver));
    }

}

package com.bilibili.Pages;

import com.bilibili.Utils.GetByLocator;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.IOException;

public class PermissionPage extends BasePage{

    public AndroidElement agreeBtn;
    public AndroidElement disagreeBtn;

    public PermissionPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public AndroidElement getAgreeBtn() throws IOException {
        return driver.findElement(GetByLocator.getLocator("agree"));
    }

    public AndroidElement getDisagreeBtn() throws IOException {
        return driver.findElement(GetByLocator.getLocator("disagree"));
    }

    public HomePage clickAgreeBtn() throws IOException {
        click(getAgreeBtn());
        return (new HomePage(driver));
    }
}

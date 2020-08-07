package com.bilibili.Pages;

import com.bilibili.Utils.GetByLocator;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.IOException;
import java.util.List;

public class HomePage extends BasePage{

    public AndroidElement loginImage;
    public AndroidElement expandsearch;
    public AndroidElement gameIcon;
    public AndroidElement messageIcon;
    public List<AndroidElement> navigations;
    public AndroidElement banner;

    public HomePage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public AndroidElement getLoginImage() throws IOException {
        return driver.findElement(GetByLocator.getLocator("loginImage"));
    }

    public AndroidElement getExpandsearch() throws IOException {
        return driver.findElement(GetByLocator.getLocator("expandSearch"));
    }

    public AndroidElement getGameIcon() throws IOException {
        return driver.findElement(GetByLocator.getLocator("gameIcon"));
    }

    public AndroidElement getMessageIcon() throws IOException {
        return driver.findElement(GetByLocator.getLocator("messageIcon"));
    }

    public List<AndroidElement> getNavigations() {
        return navigations;
    }

    public AndroidElement getBanner() {
        return banner;
    }
}

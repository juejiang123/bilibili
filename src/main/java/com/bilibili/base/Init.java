package com.bilibili.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Init {

    public static AndroidDriver<AndroidElement> initDriver() throws MalformedURLException {
        File app = new File("/Users/tianqingxia/Desktop/tv.danmaku.bili.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //devicename必填项，值可以不同
        capabilities.setCapability("deviceName", "HWI-AL00");
        //app的绝对路径必须是app所在的服务器地址
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "tv.danmaku.bili");
        capabilities.setCapability("appActivity", "tv.danmaku.bili.ui.splash.SplashActivity");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("newCommendTimeout", "600");
        capabilities.setCapability("udid", "3JU4C18416001256");
//        capabilities.setCapability("noReset", false);
        capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET,true);

//        capabilities.setCapability("fullReset",true);
//        capabilities.setCapability(AndroidMobileCapabilityType.BROWSER_NAME,"xxx");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:2233/wd/hub"), capabilities);
        return driver;
    }
}

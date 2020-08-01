package com.bilibili.base;

import com.bilibili.Utils.PropUtil;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

public class NewCaps {

    public static Capabilities initCaps(String capsPath, String udid) {
        PropUtil prop = new PropUtil(capsPath);
        DesiredCapabilities caps = new DesiredCapabilities();
        try{
            caps.setCapability("deviceName",prop.getProp("deviceName"));
            caps.setCapability("udid",udid);
            caps.setCapability("appPackage",prop.getProp("appPackage"));
            caps.setCapability("appActivity",prop.getProp("appActivity"));
            caps.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET,prop.getProp("noStopAppOnReset"));
            caps.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD,prop.getProp("resetKeyboard"));
            caps.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD,prop.getProp("unicodeKeyboard"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return caps;
    }

//    public static Capabilities initCapsWait(){
//        DesiredCapabilities caps = new DesiredCapabilities();
//        return caps;
//    }

    public static void main(String[] args) {
        String capsPath = "./src/main/resources/configs/capablities.properties";
    }
}

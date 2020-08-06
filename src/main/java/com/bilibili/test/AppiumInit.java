package com.bilibili.test;

import com.bilibili.Utils.GetByLocator;
import com.bilibili.Utils.InputGet;
import com.bilibili.base.AndroidDriverBase;
import com.bilibili.base.Init;
import com.bilibili.base.Methods;
import com.bilibili.server.Port;
import com.bilibili.server.Servers;
import com.bilibili.Log.Log;
import com.bilibili.Utils.DosCmd;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;


public class AppiumInit {
    Servers server = new Servers(new DosCmd(),new Port());
    private static Log logger = Log.getLogger(AppiumInit.class);
    String server1 = "http://127.0.0.1";
    String inputName = InputGet.getInputName();
    String capsPath = "./src/main/resources/configs/capabilities.properties";
    AndroidDriverBase driver;


    @Test
    public void killserver() throws InterruptedException {
        server.killServer();
        Thread.sleep(1000);
        logger.debug("XXXX");
    }

    @Test(dependsOnMethods = "killserver")
    public void startserver(){
        server.startServer();
        logger.info("杀死进程");

    }

    @Test
    public void login() throws IOException, InterruptedException {
        AndroidDriver<AndroidElement> driver = Init.initDriver();
        Methods methods = new Methods(driver);
        methods.bilibiliLogin();

        logger.info("1111");
    }



    @Test
    public void login2(){
        try{
            driver = new AndroidDriverBase(server1, "4723", inputName, "3JU4C18416001256", capsPath);
            System.out.println("------------------------------");
            System.out.println(driver.input);
            Methods methods = new Methods(driver);
            methods.bilibiliLogin();


        }catch (MalformedURLException e ){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Test(dependsOnMethods = "login2")
    public void operations(){
        try {
            driver.wakeUp();
            System.out.println("------------------------------");
            AndroidElement fans1 = driver.findElement(GetByLocator.getLocator("fans"));
            System.out.println(fans1);
            AndroidElement fans2 = driver.findElement(GetByLocator.getLocator("fans"), 1);
            fans2.click();
            if(driver.getPageSource().contains("我的粉丝")){
                driver.pressKey(new KeyEvent(AndroidKey.BACK));
                Thread.sleep(2000);
            }else {
                driver.pressKey(new KeyEvent(AndroidKey.BACK));
                Thread.sleep(2000);
            }
            driver.swipe("down",10);
            Thread.sleep(1000);
            AndroidElement recyclerView = driver.findElement(GetByLocator.getLocator("recyclerView"));
//            driver.swipeElement(recyclerView,"up",5);
            driver.tap(recyclerView);
            Thread.sleep(2000);
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            Thread.sleep(2000);
            boolean b = driver.swipeUntilElementAppear(GetByLocator.getLocator("setting"), "up", 10, 2);
            System.out.println(b);
            AndroidElement element = driver.swipeUtilElementAppear(GetByLocator.getLocator("setting"), "up", 5, 2);
            System.out.println(element);
//            Boolean attention2 = driver.implicitWait(GetByLocator.getLocator("attention"), 1);
//            System.out.println(attention2);
//            Boolean attention1 = driver.isElementExit(GetByLocator.getLocator("attention"), 10);
//            System.out.println(attention1);
//            boolean attention = driver.isElementExit(GetByLocator.getLocator("attention"));
//            System.out.println(attention);
            AndroidElement fans = driver.findNeedElement(GetByLocator.getLocator("fans"));
            fans.click();
            driver.quit();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test(dependsOnMethods = "login2")
    public void screenCap() throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.takeScreen("/Users/tianqingxia/IdeaProjects/frameworkTest/screenshots/","screencap.png");
        Thread.sleep(1000);
        AndroidElement vip = driver.findElement(GetByLocator.getLocator("vip"));
        driver.partialScreenForElement(vip,"/Users/tianqingxia/IdeaProjects/frameworkTest/screenshots/","subscreen");

    }
}

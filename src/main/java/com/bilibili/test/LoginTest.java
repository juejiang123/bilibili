package com.bilibili.test;

import com.bilibili.Log.Log;
import com.bilibili.Pages.BasePage;
import com.bilibili.Utils.ExcelUtil;
import com.bilibili.Utils.GetByLocator;
import com.bilibili.Utils.InputGet;
import com.bilibili.base.AndroidDriverBase;
import com.bilibili.base.Login;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class LoginTest {

    private static Log logger = Log.getLogger(AppiumInit.class);
    String server1 = "http://127.0.0.1";
    String inputName = InputGet.getInputName();
    String capsPath = "./src/main/resources/configs/capabilities.properties";
    AndroidDriverBase driver;

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        Object[][] objects = ExcelUtil.getRowData("/Users/tianqingxia/Desktop/工作簿2.xlsx", "登录");
        return objects;
    }

    @Test(dataProvider = "loginData")
    public void login(String serialNumber,String Module,String caseNumber,String caseName,String userName,String password,String assertValue,
                       String pic){
        System.out.println(serialNumber+"  "+Module+" "+caseNumber+" "+caseName+" "+userName+" "+password+" "+assertValue+" "+pic);
//        ExcelUtil util = new ExcelUtil("/Users/tianqingxia/Desktop/工作簿2.xlsx", "登录");
        try{
            driver = new AndroidDriverBase(server1, "4723", inputName, "3JU4C18416001256", capsPath);
            System.out.println("------------------------------");
            System.out.println(driver.input);
            Login login = new Login(driver);
            login.bilibiliLogin(userName,password);
            Thread.sleep(10000);
            boolean b = driver.getPageSource().contains("错误");
            System.out.println(b);
            Assert.assertEquals(b,true);
//            Thread.sleep(5000);
//            for(int i=0;i<2;i++){
//                driver.pressKey(new KeyEvent(AndroidKey.BACK));
//            }
//            Thread.sleep(1000);



        }catch (MalformedURLException e ){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Test
    public void getBili() throws IOException, InterruptedException {
        driver = new AndroidDriverBase(server1, "4723", inputName, "3JU4C18416001256", capsPath);
        AndroidElement agree = driver.findElement(GetByLocator.getLocator("agree"));
        agree.click();
        Thread.sleep(1000);
        for(int i=0;i<2;i++){
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            System.out.println("点击返回");
        }
        Thread.sleep(1000);
        System.out.println("------------------------------");
        System.out.println(driver.input);
        AndroidElement element = driver.findElement(By.id("com.huawei.android.launcher:id/workspace_screen"));
        boolean b = driver.getPageSource().contains("腾讯视频");
        System.out.println(b);
        new BasePage(driver).clickElementByCoordinate(element,6,5,20);
//        AndroidElement element = driver.findElement(By.xpath("//android.widget.TextView[19]"));
////        element.click();
//        int x = element.getCenter().getX();
//        int y = element.getCenter().getY();
//        TouchAction action = new TouchAction(driver);
//        action.tap(new PointOption().point(x,y)).release().perform();
//        Thread.sleep(1000);


    }

    @AfterMethod
    public void quit(){
        driver.quit();
    }
}

package com.bilibili.test;

import com.bilibili.Log.Log;
import com.bilibili.Utils.GetByLocator;
import com.bilibili.Utils.ImageUtil;
import com.bilibili.Utils.InputGet;
import com.bilibili.Utils.RandomUtil;
import com.bilibili.base.AndroidBase;
import com.bilibili.base.Methods;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class UploadPhoto {

    AndroidBase driver;
    private static Log logger = Log.getLogger(UploadPhoto.class);
    String server1 = "http://127.0.0.1";
    String inputName = InputGet.getInputName();
    String capsPath = "./src/main/resources/configs/capabilities.properties";

    @Test
    public void beforeClass() throws IOException, InterruptedException {
        driver = new AndroidBase(server1,"4723",inputName,"3JU4C18416001256",capsPath);
        Methods login = new Methods(driver);
        login.bilibiliLogin();

    }


    @Test(dependsOnMethods = "beforeClass")
    public void uploadPhoto() throws IOException, InterruptedException {
        driver.findElement(GetByLocator.getLocator("photo"),5).click();
        AndroidElement editPhoto = driver.findElement(GetByLocator.getLocator("editPhoto"));
        driver.partialScreenForElement(editPhoto,"./screenshots/","current");
        editPhoto.click();
        driver.findElement(GetByLocator.getLocator("permission"),5).click();
        Thread.sleep(1000);

        driver.findElement(GetByLocator.getLocator("changeAvatar")).click();
        driver.findElement(GetByLocator.getLocator("photoAlbum")).click();
        List<AndroidElement> photos = driver.findElements(GetByLocator.getLocator("photos"));
        int ranInt = new RandomUtil().getRanInt(0, 5);
        photos.get(ranInt).click();
        driver.findElement(GetByLocator.getLocator("photoChoose")).click();
        Thread.sleep(1000);
        driver.findElement(GetByLocator.getLocator("back")).click();

        driver.partialScreenForElement(editPhoto,"./screenshots/","new");
        boolean b = ImageUtil.compareImg("./screenshots/current.png", "./screenshots/new.png", 100f);
        System.out.println(b);


    }
}

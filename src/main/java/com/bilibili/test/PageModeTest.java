package com.bilibili.test;

import com.bilibili.Log.Log;
import com.bilibili.Pages.HomePage;
import com.bilibili.Pages.LoginPage;
import com.bilibili.Pages.MinePage;
import com.bilibili.Pages.PermissionPage;
import com.bilibili.Utils.ExcelUtil;
import com.bilibili.Utils.InputGet;
import com.bilibili.base.AndroidDriverBase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class PageModeTest {
    AndroidDriverBase driver;
    Log logger;

    @BeforeClass
    public void beforeClass() throws MalformedURLException {
        logger = Log.getLogger(PageModeTest.class);
        String server1 = "http://127.0.0.1";
        String inputName = InputGet.getInputName();
        String capsPath = "./src/main/resources/configs/capabilities.properties";
        driver = new AndroidDriverBase(server1,"4723",inputName,"3JU4C18416001256",capsPath);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginProvider() throws IOException {
        Object[][] excel = ExcelUtil.getRowData("/Users/tianqingxia/Desktop/工作簿2.xlsx","登录");
        return excel;
    }

    @Test(dataProvider="loginData")
    public void t001_login(String serialNumber,String Module,String caseNumber,String caseName,String userName,String password,String assertValue,
                           String pic) throws IOException, InterruptedException {
        LoginPage lp = new LoginPage(driver);
        MinePage minePage = lp.bilibiliLogin(userName, password);

        if(minePage == null){
            System.out.println("登录失败");

        }
        try {
            Assert.assertEquals(driver.getPageSource().contains(assertValue),true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}

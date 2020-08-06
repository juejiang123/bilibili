package com.bilibili.base;

import com.bilibili.Log.Log;
import com.bilibili.Utils.DosCmd;
import com.bilibili.Utils.InputGet;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AndroidDriverBase extends AndroidDriver {

    private Log logger = Log.getLogger(AndroidDriverBase.class);
    public String input;
    public String udid;

    //构造函数，创建driver对象
    public AndroidDriverBase(String server, String port, String input, String udid, String capsPath) throws MalformedURLException {
        super(new URL(server+":"+port+"/wd/hub"),new NewCaps().initCaps(capsPath,udid));
        this.input = input;
        this.udid = udid;
    }

    /**
     * 获取应用占屏幕的宽度
     * @return
     */
    public int getAppWidth(){
        int width = this.manage().window().getSize().getWidth();
        logger.info("-获取应用所占屏幕宽度:"+width);
        return width;
    }

    /**
     * 获取应用占屏幕的高度
     * @return
     */
    public int getAppHeight(){
        int height = this.manage().window().getSize().getHeight();
        logger.info("-获取应用所占屏幕高度:"+height);
        return height;
    }

    /**
     * 获取屏幕分辨率
     * @return
     */
    public Point getMobileSize(){
        //adb shell wm size
        DosCmd cmd = new DosCmd();
        Point p = null;
        List<String> list = cmd.execCmdConsole("adb shell wm size");
        String[] xes = new String[2];
        if(list.size()>0){
            xes = list.get(0).split(": ")[1].split("x");
        }

        p = new Point(Integer.valueOf(xes[0]),Integer.valueOf(xes[1]));
        return p;
    }

    /**
     * 判断元素是否存在
     * @return
     */
    public boolean isElementExit(By by){
        boolean displayed = this.findElement(by).isDisplayed();
        if(displayed){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 超时时间范围内返回元素结果，超时未找到时返回false
     * @param by
     * @param timeout
     * @return
     */
    public Boolean isElementExit(By by,int timeout){
        try{
            //显式等待
            new WebDriverWait(this,timeout).until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        }catch (Exception e){
            logger.debug("-element not found");
            return false;
        }

    }

    /**
     * 隐式等待一段时间，如果找到元素则返回
     * @param by
     * @param timout
     * @throws MalformedURLException
     */
    public Boolean implicitWait(By by,int timout){
        try{
            this.manage().timeouts().implicitlyWait(timout, TimeUnit.SECONDS);
            this.findElement(by);
            return true;
        }catch (Exception e){
            logger.debug("-element not found");
            return false;
        }
    }

    public AndroidElement findNeedElement(By by){
        try{
            AndroidElement element = (AndroidElement)this.findElement(by);
            return element;
        }catch (Exception e){
            logger.debug("-element not found");
            return null;
        }
    }

    /**
     * 重写父类的findElement方法
     * @param by
     * @return
     */
    @Override
    public AndroidElement findElement(By by){
        try{
            AndroidElement element = (AndroidElement)super.findElement(by);
            return element;
        }catch (Exception e){
            logger.debug(" -element not found");
            return null;
        }
    }

    /**
     * 显式等待定位元素
     * @param by
     * @param timeout
     * @return
     */
    public AndroidElement findElement(By by,int timeout){
        try{
            AndroidElement element = (AndroidElement)new WebDriverWait(this, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
            return element;
        }catch (Exception e){
            logger.debug(" element not found");
            return null;
        }
    }

    /**
     * 整屏滑动
     * @param direction
     * @param duration
     */
    public void swipe(String direction,int duration){
        try{
            SwipeScreen swipeScreen = new SwipeScreen(this);
            swipeScreen.swipe(direction,duration);
        }catch (Exception e){
            logger.debug("出现异常，无法滑动");
            e.printStackTrace();
        }
    }

    /**
     * 元素滑动
     * @param element
     * @param direction
     * @param duration
     */
    public void swipeElement(AndroidElement element,String direction,int duration){
        try{
            SwipeScreen swipeScreen = new SwipeScreen(this);
            swipeScreen.swipeElement(element,direction,duration);
        }catch (Exception e){
            logger.debug("操作异常");
            e.printStackTrace();
        }
    }

    /**
     * 向某方向滑动直到某个元素出现
     * @param by
     * @param direction
     * @param duration
     * @param swipeCount
     * @return
     */
    public boolean swipeUntilElementAppear(By by,String direction,int duration,int swipeCount){
        Boolean flag =false;
        while (!flag && swipeCount>0){
            AndroidElement element = this.findElement(by);
            if(element !=null){
                    flag = true;
            }else {
                    swipe(direction,duration);
                    swipeCount--;
            }
        }
        return flag;
    }

    /**
     * 向某方向滑动直到某个元素出现
     * @param by
     * @param direction
     * @param duration
     * @param swipeCount
     * @return
     */
    public AndroidElement swipeUtilElementAppear(By by,String direction,int duration,int swipeCount){
        Boolean flag = false;
        AndroidElement element = null;
        while (!flag && swipeCount>0){
            try{
                element = this.findElement(by);
                flag = true;
            }catch (Exception e){
                swipe(direction,duration);
                swipeCount--;
//                e.printStackTrace();
            }
        }
        return element;
    }

    /**
     * 在某个坐标点点击
     * @param element
     */
    public void tap(AndroidElement element){
        int x = element.getCenter().x;
        int y = element.getCenter().y;
        TouchAction action = new TouchAction(this);
        action.tap(PointOption.point(x,y));
    }

    /**
     * 长按某个坐标点
     */
    public void longPress(AndroidElement element){
        int x = element.getCenter().x;
        int y = element.getCenter().y;
        TouchAction action = new TouchAction(this);
        action.longPress(PointOption.point(x,y));
    }

    /**
     * 与服务端断开连接时，重置输入法
     */
    public void quit(){
        this.resetInput("com.baidu.input_huawei/.ImeService");
        super.quit();
    }

    public void resetInput(String input){
        try{
            String cmd = "adb shell ime set"+ input;
            DosCmd dc = new DosCmd();
            dc.execCmdConsole(cmd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 比较列表里的数据
     * @return
     */
    public Boolean listStrEquals(List<AndroidElement> strSrc,List<AndroidElement> strDes){
        Boolean flag = false;
        if((!strSrc.isEmpty()&&strSrc !=null)&&(!strDes.isEmpty()&&strDes !=null)){
            if(strSrc.size()==strDes.size()){
                for(int i=0;i<strSrc.size();i++){
                    if(strSrc.get(i).equals(strDes.get(i))){
                        flag=true;
                        continue;
                    }else {
                        flag = false;
                        break;
                    }
                }
            }else {
                System.out.println("两个list长度不同");
            }
        }else {
            System.out.println("list为空或者null");
        }
        return flag;
    }

    /**
     * 从列表中找到指定元素
     * @param elementList
     * @param index
     * @return
     */
    public AndroidElement selectElementFromList(List<AndroidElement> elementList,int index){
        AndroidElement element = null;
        if(elementList !=null &&!elementList.isEmpty()){
            if(index>0 && elementList.size()>index){
                element = elementList.get(index);
            }else {
                System.out.println("索引超出了列表长度");
            }
        }else {
            System.out.println("未找到元素");
        }
        return element;
    }

    /**
     * 实现滑动前后字符串集合list的对比
     */
    public void swipeUntilBoundary(String direction,By by) throws InterruptedException {
        Boolean flag = false;
        while (!flag){
            List<AndroidElement> strSrc = new ArrayList<>();
            List<AndroidElement> strDes = new ArrayList<>();
            //滑动前定位元素并将元素的text添加到集合strSrc里
            List<AndroidElement> elementOld = super.findElements(by);
            for(AndroidElement element:elementOld){
                strSrc.add(element);
            }
            this.swipe(direction,500);
            this.wait();

            List<AndroidElement> elementNew = super.findElements(by);
            for(AndroidElement ele : elementNew){
                strDes.add(ele);
            }

            Boolean bool = this.listStrEquals(strSrc, strDes);
            flag = bool;
        }
    }

    /**
     * 设备返回操作
     * @throws InterruptedException
     */
    public void pressBack() throws InterruptedException {
        this.wait(500);
        super.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    /**
     * 多次返回
     * @param number
     */
    public void pressBack(int number) throws InterruptedException {
       if(number>0){
           for(int i=0;i<number;i++){
               this.pressBack();
               System.out.println("执行第"+(i+1)+"次返回");
           }
       }else {
           System.out.println("参数有误");
       }
    }

    /**
     * 点击home键
     */
    public void pressHome() throws InterruptedException {
        this.wait(500);
        super.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    /**
     * 点击回车键
     * @throws InterruptedException
     */
    public void pressEnter() throws InterruptedException {
        this.wait(500);
        super.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    /**
     * 手机键盘删除操作
     */
    public void delete() throws InterruptedException {
        this.wait(200);
        super.pressKey(new KeyEvent(AndroidKey.DEL));
    }

    /**
     * 手机键盘多次删除
     */
    public void pressBackspace(int number) throws InterruptedException {
        if(number>0){
            for(int i=0;i<number;i++){
                this.wait(100);
                this.delete();
            }
        }else {
            System.out.println("参数错误");
        }
    }

    /**
     * 唤醒屏幕，版本变化可以直接调用
     */
    public void wakeUp(){
        try{
            if(super.isDeviceLocked()){
                super.unlockDevice();
            }else {
                System.out.println("未锁屏不用唤醒");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 截图全屏
     */
    public void takeScreen(String path,String fileName) throws IOException {
        File srcFile = super.getScreenshotAs(OutputType.FILE);
        File desFile = new File(path+fileName);
        FileUtils.copyFile(srcFile,desFile);
    }

    public void takeScreenForElemment(AndroidElement ele,String path,String fileName) throws IOException {
        //获得element的位置和大小
        Point location = ele.getLocation();
        Dimension size = ele.getSize();
        byte[] imageByte = super.getScreenshotAs(OutputType.BYTES);
        //创建全屏截图
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageByte));
        //截取element所在位置的子图
        BufferedImage subimage = originalImage.getSubimage(
                location.getX(), location.getY(), size.getWidth(), size.getHeight());
        try{
            ImageIO.write(subimage,"png",new File(path+fileName+".png"));
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    /**
     * 屏幕的部分图片截图
     * @param ele
     * @param path
     * @param filename
     * @throws IOException
     */
    public void partialScreenForElement(AndroidElement ele,String path,String filename) throws IOException {
        Point location = ele.getLocation();
        Dimension size = ele.getSize();
        byte[] screenPic = super.getScreenshotAs(OutputType.BYTES);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(screenPic));
        BufferedImage subimage = bufferedImage.getSubimage(location.getX(), location.getY(), size.getWidth(), size.getHeight());
        boolean png = ImageIO.write(subimage, "png", new File(path + filename + ".png"));
        System.out.println(png);
    }



    public static void main(String[] args) throws MalformedURLException {
        String server = "http://127.0.0.1";
        String inputName = InputGet.getInputName();
        String capsPath = "./src/main/resources/configs/capabilities.properties";
        AndroidDriverBase driver = new AndroidDriverBase(server, "4723", inputName, "3JU4C18416001256", capsPath);
    }
}

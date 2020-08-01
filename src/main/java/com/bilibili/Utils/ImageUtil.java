package com.bilibili.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    //把文件转成数组，数组存放像素的数字
    public static int[] getData(String name){
        try{
            BufferedImage img = ImageIO.read(new File(name));
            BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(img,0,0,100, 100,null);
            int[] data = new int[256];
            for(int x=0;x<bufferedImage.getWidth();x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    int rgb = bufferedImage.getRGB(x, y);
                    System.out.println("xxx-->"+x +"   y--->"+y);
                    Color color = new Color(rgb);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    data[(red + green + blue) / 3]++;

                }
            }
            return data;
            }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //对比数组，返回比例值
    public static float compare(int[] s,int[] t){
        float result = 0F;
        for(int i=0;i<256;i++){
            int abs = Math.abs(s[i]-t[i]);
            int max = Math.max(s[i],t[i]);
            result += 1-(float) abs / (max == 0 ? 1 : max);

        }
        return (result/256)*100;
    }

    //对比图片，判断预期是否完全达到
    public static boolean compareImg(String srcName,String desName,float f) throws IOException {
        int[] data = getData(srcName);
        int[] data1 = getData(desName);
        float compare = compare(data, data1);
        System.out.println(compare);
        if(compare >= f){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        String src = "/Users/tianqingxia/Desktop/保险合同和照片/WechatIMG194.jpeg";
        String des = "/Users/tianqingxia/Desktop/WX20200719-230112.png";
        boolean b = compareImg(src, des, 90f);
        System.out.println(b);
    }

}

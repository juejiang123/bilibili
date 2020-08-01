package com.bilibili.Utils;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlUtil {

    public static List<String> readXML(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filename));
        Element root = document.getRootElement();
        List<Element> deviceID = root.elements("deviceID");
        List<String> deviceData = new ArrayList<>();
        for(Element dev : deviceID){
            for(Iterator iter = dev.elementIterator();iter.hasNext();){
                Element next =(Element) iter.next();
                deviceData.add(next.getText());
            }
        }
        return deviceData;
    }


    public static void readXML1(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filename));
        Element root = document.getRootElement();
        List<Element> student = root.elements("student");
        for(Element stu : student){
            System.out.println(stu.attributeValue("id"));
        }
    }

    public static void writeXML(String filename) throws IOException {
        Document d = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("teacher");
        d.setRootElement(root);
        root.addAttribute("name","tqx");
        String[] stuArr={"111","aaa","222","bbb"};
        for(int i=0;i<stuArr.length;i++){
            Element stu = root.addElement("student");
            stu.addAttribute("id",String.valueOf(i));
            stu.setText(stuArr[i]);
        }
        OutputFormat format = new OutputFormat("    ",true);
        XMLWriter w = new XMLWriter(new FileOutputStream(filename),format);
        w.write(d);
        w.close();

    }

    public static void createXMLConfig(List<String> deviceList,List<Integer> appiumPortList) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element device = DocumentHelper.createElement("device");
        document.setRootElement(device);
        device.addAttribute("name","appiumstartlist");
        if(deviceList.size()>0){
            for(int i=0;i<deviceList.size();i++){
                Element deviceID = device.addElement("deviceID");
                deviceID.addAttribute("id",String.valueOf(i));
                Element deviceName = deviceID.addElement("deviceName");
                Element appiumPort = deviceID.addElement("appiumPort");
                deviceName.setText(deviceList.get(i));
                appiumPort.setText(String.valueOf(appiumPortList.get(i)));
            }
            OutputFormat format = new OutputFormat("    ",true);
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("device.xml"),format);
            xmlWriter.write(document);
            xmlWriter.close();


        }
    }

    public static void main(String[] args) throws DocumentException, IOException {
//        writeXML("test.xml");
//        readXML1("test.xml");
        List<String> devicelist = new ArrayList<>();
        for(int i=0;i<3;i++){
            devicelist.add("111"+i);
        }
        List<Integer> appiumPortList = new ArrayList<>();
        for(int j=0;j<3;j++){
            appiumPortList.add(888+j);
        }
        createXMLConfig(devicelist,appiumPortList);
        List<String> list = readXML("device.xml");
        System.out.println(list);


    }

}

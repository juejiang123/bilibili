package com.bilibili.Utils;

import com.bilibili.server.Port;
import com.bilibili.server.Servers;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlUtil {

    /**
     * 读取设备xml文件，返回xml元素值列表
     * @param filename
     * @return
     * @throws DocumentException
     */
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

    /**
     * 读取xml文件内容
     * @param filename
     * @throws DocumentException
     */
    public static void readXML1(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filename));
        Element root = document.getRootElement();
        List<Element> student = root.elements("student");
        for(Element stu : student){
            System.out.println(stu.attributeValue("id"));
        }
    }

    /**
     * 文件写入练习
     * @param filename
     * @throws IOException
     */
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

    /**
     * 不同test执行相同的测试类
     * @param classNames
     * @throws DocumentException
     */
    public static void createTestngXml(String[] classNames) throws DocumentException {
        Servers servers = new Servers(new DosCmd(),new Port());
        List<String> devices = servers.getDevices();
//        List<Integer> portList = servers.getPortList(4723);
//        List<Integer> bpList = servers.getPortList(5560);
        Document document = DocumentHelper.createDocument();
        Element suite = DocumentHelper.createElement("suite");
        document.setRootElement(suite);
        suite.addAttribute("name","appiumtest");
        suite.addAttribute("parallel","tests");
        suite.addAttribute("thread-count",String.valueOf(devices.size()));
        Element listeners = suite.addElement("listeners");
        Element listener = listeners.addElement("listener");
        listener.addAttribute("class-name","com.vimalselvam.testng.listener.ExtentTestNgFormatter");
        List<String> s = readXML("device.xml");
        //{"192.168.1.5","4430"}
        for(int j=0;j<devices.size();j++){
            Element test = suite.addElement("test");
            Element para = test.addElement("parameter");
            para.addAttribute("name","udid");
            para.addAttribute("value",s.get(2*j));
            Element para1 = test.addElement("parameter");
            para1.addAttribute("name","port");
            para1.addAttribute("value",s.get(2*j+1));
            Element classn = test.addElement("classes");
            for(int i=0;i<classNames.length;i++){
                Element classnode = classn.addElement("class");
                classnode.addAttribute("name",classNames[i]);
            }

        }
        OutputFormat opf = new OutputFormat("    ",true);
        XMLWriter xmlWriter;
        try{
            xmlWriter = new XMLWriter(new FileOutputStream("testng.xml"), opf);
            xmlWriter.write(document);
            xmlWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 不同test执行不同的测试类
     * @param classNames
     * @throws DocumentException
     */
    public static void createTestngXml1(String[] classNames) throws DocumentException {
        Servers servers = new Servers(new DosCmd(),new Port());
        List<String> devices = servers.getDevices();
        Document document = DocumentHelper.createDocument();
        Element suite = DocumentHelper.createElement("suite");
        document.setRootElement(suite);
        suite.addAttribute("name","appiumtest");
        suite.addAttribute("parallel","tests");
        suite.addAttribute("thread-count",String.valueOf(devices.size()));
        Element listeners = suite.addElement("listeners");
        Element listener = listeners.addElement("listener");
        listener.addAttribute("class-name","com.vimalselvam.testng.listener.ExtentTestNgFormatter");
        List<String> s = readXML("device.xml");
        //{"192.168.1.5","4430"}
        for(int j=0;j<devices.size();j++){
            Element test = suite.addElement("test");
            Element para = test.addElement("parameter");
            para.addAttribute("name","udid");
            para.addAttribute("value",s.get(2*j));
            Element para1 = test.addElement("parameter");
            para1.addAttribute("name","port");
            para1.addAttribute("value",s.get(2*j+1));
            Element classes = test.addElement("classes");
            Element classnode = classes.addElement("class");
            classnode.addAttribute("name",classNames[j]);
        }
        OutputFormat opf = new OutputFormat("    ",true);
        XMLWriter xmlWriter;
        try{
            xmlWriter = new XMLWriter(new FileOutputStream("testng1.xml"), opf);
            xmlWriter.write(document);
            xmlWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws DocumentException, IOException {
//        writeXML("test.xml");
//        readXML1("test.xml");
//        List<String> devicelist = new ArrayList<>();
//        for(int i=0;i<3;i++){
//            devicelist.add("111"+i);
//        }
//        List<Integer> appiumPortList = new ArrayList<>();
//        for(int j=0;j<3;j++){
//            appiumPortList.add(888+j);
//        }
//        createXMLConfig(devicelist,appiumPortList);
//        List<String> list = readXML("device.xml");
//        System.out.println(list);
//
        String[] classNames = new String[3];
        classNames[0]="com.bilibili.test.AppiumInit";
        classNames[1]="com.bilibili.test.LoginTest";
        classNames[2]="com.bilibili.test.UploadPhoto";
        createTestngXml(classNames);
        createTestngXml1(classNames);
    }

}

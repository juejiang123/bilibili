package com.bilibili.Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import javax.print.DocFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    //整个Excel对象
    private XSSFWorkbook workbook;
    //文件sheet工作表
    private XSSFSheet ExcelSheet;
    //单元格
    private XSSFCell cell;
    //行
//    private XSSFRow row;

    private String filePath;
    //行
    private static Row row;


    //设定要操作的Excel的文件路径和Excel文件中的sheet名称
    //在读写Excel的时候，均需要先调用此方法，设定要操作的Excel文件路径和要操作的sheet名称
    public ExcelUtil(String path,String sheetName){
        FileInputStream ExcelFile;
        try{
            ExcelFile = new FileInputStream(path);
            workbook = new XSSFWorkbook(ExcelFile);
            ExcelSheet = workbook.getSheet(sheetName);
        }catch (Exception e){
            e.printStackTrace();
        }
        filePath = path;
    }

    public String getCellData(int RowNum,int ColNum){
        cell = ExcelSheet.getRow(RowNum).getCell(ColNum);
        String CellData = "";
        //4.1.2版本poi判断单元格内容格式方法和3.12版本不同
        if(cell.getCellType()== CellType.STRING ){
             CellData = cell.getStringCellValue();
        }else if(cell.getCellType() == CellType.NUMERIC){
            DecimalFormat df = new DecimalFormat();
            df.setGroupingUsed(false);
            CellData = df.format(cell.getNumericCellValue());
        }
        return CellData;
    }

    public void setCellData(int RowNum,int ColNum,String result) throws IOException {
        row = ExcelSheet.getRow(RowNum);
        //4.1.2版本poi获取空白单元格方法和3.12版本不同
        cell = (XSSFCell) row.getCell(ColNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if(cell==null){
            cell = (XSSFCell) row.createCell(ColNum);
            cell.setCellValue(result);
        }else {
            cell.setCellValue(result);
        }

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.flush();
        fos.close();


    }

    public int getLastColumnNum(){
        int lastCellNum = ExcelSheet.getRow(0).getLastCellNum()-1;
        System.out.println(lastCellNum);
        return lastCellNum;
    }

    public static Object[][] getRowData(String excelFilePath,String sheetName) throws IOException {
        File file = new File(excelFilePath);
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook1 = null;
        int index = excelFilePath.indexOf(".");
        String fileExtensionName = excelFilePath.substring(index);
        System.out.println(fileExtensionName);
        if(fileExtensionName.equals(".xlsx")){
            workbook1 = new XSSFWorkbook(fis);
        }else if(fileExtensionName.equals(".xls")){
            workbook1 = new HSSFWorkbook(fis);
        }
        Sheet sheet = workbook1.getSheet(sheetName);
        int rowNum = sheet.getLastRowNum()-sheet.getFirstRowNum();
        List<Object[]> records = new ArrayList<>();
        for(int i=1;i<=rowNum;i++){
            Row row = sheet.getRow(i);
            int colnum = row.getLastCellNum()- 2;
            String[] fields = new String[row.getLastCellNum()-2];
            System.out.println(row.getCell(row.getLastCellNum()-2).getStringCellValue());
            if(row.getCell(row.getLastCellNum()-2).getStringCellValue().equalsIgnoreCase("y")){
                for(int j=2;j<colnum;j++){
                    if(row.getCell(j).getCellType()==CellType.STRING){
                        fields[j] = row.getCell(j).getStringCellValue();
                    }else if(row.getCell(j).getCellType()==CellType.NUMERIC){
                        DecimalFormat df = new DecimalFormat("0");
                        fields[j] = df.format(row.getCell(j).getNumericCellValue());
                    }else{
                        System.out.println("格式错误");
                    }
                }
            }

            records.add(fields);
        }
        Object[][] results = new Object[records.size()][];
        for(int i=0;i<records.size();i++){
            results[i]=records.get(i);
        }
        return results;

    }

    public static void main(String[] args) throws IOException {
        ExcelUtil eu = new ExcelUtil("/Users/tianqingxia/Desktop/工作簿2.xlsx","登录");
//        String cellData = eu.getCellData(1, 4);
//        System.out.println(cellData);
//        eu.setCellData(2,9,"test");
//        eu.getLastColumnNum();
        Object[][] objects = eu.getRowData("/Users/tianqingxia/Desktop/工作簿2.xlsx", "登录");
        for(int i=0;i<objects.length;i++){
            Object[] object = objects[i];
            System.out.println("===========");
            for(int j=0;j<object.length;j++){
                System.out.println(object[j]);
            }
        }
    }



}

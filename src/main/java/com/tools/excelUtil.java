package com.tools;

import java.io.FileInputStream;

import com.testConfig.Constant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class excelUtil {
    private static XSSFSheet sheet;
    private static XSSFWorkbook workbook;
    private static XSSFCell cell;
    private static XSSFRow row;

    //指定要操作的excel文件的路径及sheet名称
    public static void setExcelFile(String path,String sheetName) throws Exception{
        try {
            FileInputStream file = new FileInputStream(path);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取excel文件指定单元格数据(此方法只针对.xlsx后辍的Excel文件)
    public static String getCellData(int rowNum,int colNum) throws Exception{
        try {
            //获取指定单元格对象
            cell = sheet.getRow(rowNum).getCell(colNum);
            //获取单元格的内容
            //如果为字符串类型，使用getStringCellValue()方法获取单元格内容，如果为数字类型，则用getNumericCellValue()获取单元格内容
            String cellData = cell.getStringCellValue();
            return cellData;
        } catch (Exception e) {
            return "";
        }
    }

    //在EXCEL的执行单元格中写入数据(此方法只针对.xlsx后辍的Excel文件) rowNum 行号，colNum 列号
    public static void setCellData(int rowNum,int colNum,String Result) throws Exception{
        try {
            //获取行对象
            row = sheet.getRow(rowNum);
            //如果单元格为空，则返回null
            cell = row.getCell(colNum);
            if(cell == null){
                cell=row.createCell(colNum);
                cell.setCellValue(Result);
            }else{
                cell.setCellValue(Result);
            }
            FileOutputStream out = new FileOutputStream(Constant.FilePath);
            if(Result.equals("Pass")){
                CellStyle cellStyle = workbook.createCellStyle(); //填充单元格
                cellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());    //填黑色
                cell.setCellStyle(cellStyle);
            }else if(Result.equals("Fail")){
                CellStyle cellStyle = workbook.createCellStyle(); //填充单元格
                cellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());    //填黑色
                cell.setCellStyle(cellStyle);
            }
            //将内容写入excel中
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从EXCEL文件中获取测试数据
    public static Object[][] getTestData(String excelFilePath,String sheetName) throws IOException {
        //声明一个file文件对象
        File file = new File(excelFilePath);
        //创建一个输入流
        FileInputStream in = new FileInputStream(file);
        //声明workbook对象
        Workbook workbook = null;
        //判断文件扩展名
        String fileExtensionName = excelFilePath.substring(excelFilePath.indexOf("."));
        if(fileExtensionName.equals(".xlsx")){
            workbook = new XSSFWorkbook(in);
        }else {
            workbook = new HSSFWorkbook(in);
        }
        //获取sheet对象
        Sheet sheet = workbook.getSheetAt(0);
        //获取sheet中数据的行数,行号从0始
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

        List<Object[]> records = new ArrayList<Object[]>();
        //读取数据（省略第一行表头）
        for(int i=1; i<rowCount+1; i++){
            //获取行对象
            Row row = sheet.getRow(i);
            System.out.println(">>>>>>>>>>> "+ row.getLastCellNum());
            //声明一个数组存每行的测试数据,excel最后两列不需传值
            String[] fields = new String[row.getLastCellNum()-2];
            //excel倒数第二列为Y，表示数据行要被测试脚本执行，否则不执行
            System.out.println("yeshi:" + row.getLastCellNum());
            Cell cell1=row.getCell(row.getLastCellNum()-1);
            System.out.println("输出的值111："+row.getCell(row.getLastCellNum()-2).getStringCellValue());
            if(row.getCell(row.getLastCellNum()-2).getStringCellValue().equals("Y")){
                //if(row.getCell(10).getStringCellValue().equals("Y")){
                for(int j=0; j<row.getLastCellNum()-2; j++){
                    //System.out.println("cell:"+row.getCell(j));
                    //判断单元格数据是数字还是字符
                    if (row.getCell(j)!=null) {
                        switch (row.getCell(j).getCellType()) {
                            case NUMERIC:
                                fields[j] = String.valueOf(row.getCell(j).getNumericCellValue());
                                //System.out.println(fields[j]+":"+String.valueOf(row.getCell(j).getNumericCellValue()));
                                break;
                            case STRING:
                                fields[j] = row.getCell(j).getStringCellValue();
                                //System.out.println(fields[j]+":"+row.getCell(j).getStringCellValue());
                                break;
                            default:
                                fields[j] = row.getCell(j).getStringCellValue();
                                break;
                        }
                    }
                }

                /*for (int k=0;k<fields.length;k++){
                    System.out.println("*****:"+fields>>>>>>>>>>>[k]);
                }*/
                //System.out.println("********:"+sheet.getRow(0).getLastCellNum());
                records.add(fields);
            }else {
                System.out.println("输出的值111："+row.getCell(row.getLastCellNum()-2).getStringCellValue());
                System.out.println("没有可执行case");
            }
        }
        //将list转为Object二维数据
        Object[][] results = new Object[records.size()][];
        //设置二维数据每行的值，每行是一个object对象
        for(int i=0; i<records.size(); i++){
            results[i]=records.get(i);
        }
        return results;
    }

    public static int getLastColumnNum() throws Exception {
        //返回数据文件最后一列的列号，如果有12列则返回11

        return sheet.getRow(0).getLastCellNum()-1;
    }

    public static int getResponseNum() throws Exception {
        //返回数据文件最后一列的列号，如果有12列则返回11

        return sheet.getRow(0).getLastCellNum()-3;
    }
}

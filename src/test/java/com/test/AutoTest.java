package com.test;
import com.alibaba.fastjson.JSONObject;

import com.testConfig.Constant;
import com.testConfig.RestClient;
import com.tools.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AutoTest {

    @DataProvider(name="testData")
    public static Object[][] data() throws Exception{

        Object[][] data = excelUtil.getTestData(Constant.FilePath,Constant.FileSheet);
        System.out.println("获取到的第一个值为："+data.length);
        return data;
    }


    @Test(dataProvider="testData",description="测试接口")
    public void testApi(
            String rowNumber,
            String caseRowNumber,
            String testCaseName,
            String priority,
            String apiName,
            String url,
            String type,
            String parmsType,
            String parms,
            String assertKeyWord,
            String caseRely,             //依赖的caseid
            String relyReturn,            //依赖的返回值
            String relyField,             //依赖字段
            String respoonse
    ) throws Exception{
        System.out.println("用例名称：："+"response: "+testCaseName);
        Log.startTestCase(testCaseName);
        //定义统计成功和失败的个数
        List<String> passCount=new ArrayList<>();
        List<String> failCount=new ArrayList<>();
        //处理数据依赖
        if(caseRely!=null) {
            int rowNumberInt = Integer.parseInt(rowNumber.split("[.]")[0]);
            int caseRelyInt = Integer.parseInt(caseRely.split("[.]")[0]);
            if (caseRelyInt<rowNumberInt){
                JSONObject relyResponse = null;
                relyResponse = JSONObject.parseObject(excelUtil.getCellData(caseRelyInt, excelUtil.getResponseNum()));
                Object r1 = relyResponse.get(relyReturn);
                JSONObject jsonParms = JSONObject.parseObject(parms);
                jsonParms.put(relyField, r1);
                parms = jsonParms.toString();
            }
        }

        JSONObject responseObject = null;
        if ("post".equalsIgnoreCase(type)||"json".equalsIgnoreCase(parmsType)){
            responseObject= RestClient.post(url,parms);
            excelUtil.setCellData(Integer.parseInt(rowNumber.split("[.]")[0]),excelUtil.getResponseNum(),responseObject.toString());
            Log.info("Responce: "+responseObject.toString());
            //WriteResponce.write(Constant.ResponseSheet,responseObject.toString(),Integer.parseInt(rowNumber.split("[.]")[0]),0);
        }else if("GET".equalsIgnoreCase(type)){
            responseObject=RestClient.get(url);
            excelUtil.setCellData(Integer.parseInt(rowNumber.split("[.]")[0]),excelUtil.getResponseNum(),responseObject.toString());
            Log.info("Responce: "+responseObject.toString());
        }

        Log.info("断言Response是否与预期结果一致: "+assertKeyWord);
        try {
            //Assert.assertTrue(responseObject.toString().contains(assertKeyWord));
//            System.out.println(responseObject.toString());
//            System.out.println(assertKeyWord);
            Assert.assertTrue(JSONPathUtil.checkPoint(responseObject.toString(),assertKeyWord));

        } catch (AssertionError  error){
            Log.info("断言Response是否与预期结果一致: "+assertKeyWord +" ---> 断言失败");
            excelUtil.setCellData(Integer.parseInt(rowNumber.split("[.]")[0]), excelUtil.getLastColumnNum(), "Fail");
            failCount.add(rowNumber);
            Reporter.log(testCaseName+"接口返回数据成功，但是断言失败，你想要的结果是："+assertKeyWord+"但是实际结果是："+responseObject.toString());
            Log.info("测试结果成功写入excel数据文件中的测试执行结果列");
            Assert.fail("断言Response是否与预期结果一致: "+assertKeyWord +" 失败");
        }

        //System.out.println("**** "+Integer.parseInt(rowNumber.split("[.]")[0]));
        //ExcelUtils.setCellData(Integer.parseInt(rowNumber.split("[.]")[0]),10,"测试执行成功");
        Log.info("断言Response是否与预期结果一致: "+assertKeyWord +" ---> 断言成功");
        excelUtil.setCellData(Integer.parseInt(rowNumber.split("[.]")[0]),excelUtil.getLastColumnNum(),"Pass");
        passCount.add(rowNumber);
        Reporter.log(testCaseName+"测试成功，并且断言结果正确");
        Log.info("测试结果成功写入excel数据文件中的测试执行结果列");
        Log.endTestCase(testCaseName);
    }


    @BeforeMethod
    public void beforeClass() throws Exception{
        excelUtil.setExcelFile(Constant.FilePath,Constant.FileSheet);
    }

}

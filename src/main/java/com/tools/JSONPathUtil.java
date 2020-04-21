package com.tools;
import com.alibaba.fastjson.JSONPath;

import java.util.HashMap;
import java.util.Map;

public class JSONPathUtil {

    public static void main(String[] args) {

        String json = "{\"store\":{\"book\":[{\"title\":\"高效Java\",\"price\":10.2},{\"title\":\"设计模式a\",\"price\":12.21},{\"title\":\"重构\",\"isbn\":\"553\",\"price\":8},{\"title\":\"虚拟机\",\"isbn\":\"395\",\"price\":22}],\"bicycle\":{\"color\":\"red\",\"price\":19}}}";
        //String expression1  = (String) JSONPath.read(json,"$.store.book[0].title");
        //int expression2 = (int) JSONPath.read(json,"$.store.book[0].price");

        // 设置的检查点,多个检查点用;分隔
        String params = "$.store.book[0].price=10.2;$.store.book[1].title=设计模式";
        String[] data = params.split(";");
        // 定义测试结果的标记
        Boolean flag = false;

        //遍历数组，获取每一个检查点在json中对应的数据，存在map中
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            map.put(data[i].split("=")[0], data[i].split("=")[1]);
            System.out.println("检查点"+ (i+1) +"返回的数据：" + JSONPath.read(json, data[i].split("=")[0]));
            System.out.println("检查点"+ (i+1) +"断言的数据：" + map.get(data[i].split("=")[0]));

            //判断检查点数据与返回的json数据是否一致
            if (JSONPath.read(json, data[i].split("=")[0]) instanceof String) {
                if (JSONPath.read(json, data[i].split("=")[0]).equals(map.get(data[i].split("=")[0]))) {
                    //System.out.println("Pass A");
                    flag = true;
                } else {
                    //System.out.println("Fail A");
                    flag = false;
                    break;
                }
            } else { // Object转String
                if ((JSONPath.read(json, data[i].split("=")[0]).toString()).equals((map.get(data[i].split("=")[0])))) {
                    //System.out.println("Pass B");
                    flag = true;
                } else {
                    //System.out.println("Fail B");
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            System.out.println("【测试执行结果：通过】");
        } else {
            System.out.println("【测试执行结果：失败】");
        }
    }

    /**
     * 预期结果校验
     * @param response
     * @param assertKeyWord
     * @return
     */
    public static Boolean checkPoint(String response,String assertKeyWord){
        //分隔检查点
        String[] data = assertKeyWord.split(";");
        // 定义测试结果的标记
        Boolean flag = false;

        //遍历数组，获取每一个检查点在json中对应的数据，存在map中
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            map.put(data[i].split("=")[0], data[i].split("=")[1]);
            System.out.println("检查点"+ (i+1) +"返回的数据：" + JSONPath.read(response, data[i].split("=")[0]));
            System.out.println("检查点"+ (i+1) +"断言的数据：" + map.get(data[i].split("=")[0]));

            Log.info("检查点"+ (i+1) +"返回的数据：" + JSONPath.read(response, data[i].split("=")[0]));
            Log.info("检查点"+ (i+1) +"断言的数据：" + map.get(data[i].split("=")[0]));

            //判断检查点数据与返回的json数据是否一致
            if (JSONPath.read(response, data[i].split("=")[0]) instanceof String) {
                if (JSONPath.read(response, data[i].split("=")[0]).equals(map.get(data[i].split("=")[0]))) {
                    //System.out.println("Pass A");
                    flag = true;
                } else {
                    //System.out.println("Fail A");
                    flag = false;
                    break;
                }
            } else { // Object转String
                if ((JSONPath.read(response, data[i].split("=")[0]).toString()).equals((map.get(data[i].split("=")[0])))) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return true;
        } else {
            return false;
        }
    }
}
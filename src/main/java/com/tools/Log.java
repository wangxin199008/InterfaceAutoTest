package com.tools;
import org.apache.log4j.Logger;

public class Log {
    // 初始化Log4j日志
    private static Logger Log = Logger.getLogger(com.tools.Log.class.getName());

    // 打印测试用例开头的日志
    public static void startTestCase(String sTestCaseName) {
        Log.info("------------------ 测试用例【"+ sTestCaseName + "】" + "开始执行 ------------------");
    }

    //打印测试用例结束的日志
    public static void endTestCase(String sTestCaseName) {
        Log.info("------------------ 测试用例【"+ sTestCaseName + "】" + "测试执行结束 ------------------");

    }

    public static void info(String message) {
        Log.info(message);
    }

    public static void warn(String message) {
        Log.warn(message);
    }

    public static void error(String message) {
        Log.error(message);
    }

    public static void fatal(String message) {
        Log.fatal(message);
    }

    public static void debug(String message) {
        Log.debug(message);
    }

}

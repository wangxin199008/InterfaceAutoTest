package com.test;

import org.testng.annotations.Test;

import javax.xml.soap.Node;
import java.io.File;
import java.util.Scanner;
import java.util.Stack;

public class test {

    public static void sort(int[] data, int left, int right) {
        if (left < right) {
            // 首先找出中间的索引
            int center = (left + right) / 2;
            // 对中间索引左边的数组进行递归
            sort(data, left, center);
            // 对中间索引右边的数组进行递归
            sort(data, center + 1, right);
            // 合并
            merge(data, left, center, right);
        }
    }

    public static void merge(int[] data, int left, int center, int right) {
        int[] tmpArr = new int[data.length];
        int mid = center + 1;
        // third记录中间数组的索引
        int third = left;
        int tmp = left;
        while (left <= center && mid <= right) {
            // 将两个数组中取出最小的数放入中间数组
            if (data[left] <= data[mid]) {
                tmpArr[third++] = data[left++];
            } else {
                tmpArr[third++] = data[mid++];
            }
        }

        // 剩余部分依次放入中间数组
        while (mid <= right) {
            tmpArr[third++] = data[mid++];
        }
        while (left <= center) {
            tmpArr[third++] = data[left++];
        }
        while(tmp <= right){
             data[tmp] = tmpArr[tmp++];
        }
    }
    public static void main1(String[] args) {
        int[] a = { 3, 2,33,21,44, 5, 4 };
        sort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    @Test
    public void maopao(){
        int[] num= new int[]{1,4,77,11,44,7,22};
        for (int i=0;i<num.length-1;i++){
            for(int j=0;j<num.length-1-i;j++){
                if(num[j]>num[j+1]){
                    int temp =num[j];
                    num[j]=num[j+1];
                    num[j+1]=temp;
                }
            }
        }
        for (int i=0;i<num.length;i++) {
            System.out.println(num[i]);
        }
    }
    @Test
    public void test01(){
        String s="草原下面马儿跑,我们的祖国是草原. 草原的花朵真鲜艳.";
        String s1=s.replace(".","~~~");
        System.out.println(s1);

        String a[] = {"sbg","dyh","yhjjjj"};
        for (int i=0;i<a.length-1;i++){
            for(int j=0;j<a.length-1-i;j++){
                if(a[j].compareTo(a[j+1])>0){
                    String temp =a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        for (int i=0;i<a.length;i++) {
            System.out.println(a[i]);
        }

    }
    @Test
    public void mian1(){
        File file = new File("f:");
        listAllFile(file);
    }


    public static void listAllFile(File file) {
        File[] strs = file.listFiles();
        for (int i = 0; i < strs.length; i++) {
            // 判断strs[i]是不是目录
            if (strs[i].isDirectory()) {
               // listAllFile(strs[i]);//递归调用自己
                System.out.println("目录="+strs[i].getName());
            } else {
                System.out.println("文件名="+strs[i].getName());
            }
        }
    }
    @Test
    public void test2() throws Exception {

        System.out.println(fib(5));
    }
    public static  int fib(int n) throws Exception {
        if(n<0)
            throw new Exception("参数不能为负！");
        else if(n==1 || n==0)
            return n;
        else
            return fib(n-1)+fib(n-2);



    }
    @Test
    public void test3() throws Exception {

        System.out.println(fib1(100));

    }
    public static int fib1(int n) throws Exception {
        if(n==1){

            return 1;
        }else {
            System.out.print(n+fib1(n-1));
            return n+fib1(n-1);
        }

    }
    @Test
    public void test05(){
        String a = "555555555";
        String b = "6";
        int c = Integer.parseInt(a)+Integer.parseInt(b);
        System.out.println(c);
    }


    @Test
    public void cheng99(){
        for(int i =1;i<=9;i++){
            for(int j=1;j<=i;j++){
                System.out.print(j+"*"+i+"="+i*j + " ");
            }
            System.out.println();
        }
    }
    @Test
    public void  test07(){
        int[] arrays=new  int[]{1,3,5,7,9,11,15,17,19,23,27,29};
        int key = 9;
        int low =0;
        int high=arrays.length-1;
        int i = recursiveBinarySearch(arrays,low,high,key);
        System.out.println(i);
    }


    public static int recursiveBinarySearch(int[] arrays, int low,int high,int key){
        int mid = low+(high-low)/2;
        if(low > high || key<arrays[low] || key > arrays[high]){
            return -1;
        }
        if(arrays[mid]>key){
            return recursiveBinarySearch(arrays,low,mid-1,key);
        }else if(arrays[mid]<key){
            return recursiveBinarySearch(arrays,mid+1,high,key);
        }else
            return mid;
    }
    @Test
    public void test09(){
        int[] a={1,2,5,7,8,44,11,22,44,55,66,88,5,12};
        for(int i=0;i<a.length;i++){
            for(int j=i+1;j<a.length;j++){
                for(int z=j+1;z<a.length;z++){
                    if((a[i]+a[j]+a[z])%2!=0 || a[i] !=a[j] || a[j]!=a[z] || a[i]!=a[z]){
                        System.out.println(a[i]+" "+a[j]+" "+ a[z]);
                    }
                }
            }
        }
    }

    public static void main(String arg[]){
        int H = 7, W = 7;//高和宽必须是相等的奇数
        for(int i=0;i<(H+1)/2;i++){
            for(int j=0;j<W/2-i;j++){
                System.out.print(" ");
            }
            for(int k=1;k<(i+1)*2;k++){
                System.out.print("*");
            }
            System.out.println();
        }
    }

}



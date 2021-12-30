package com.geekbrains.lesson9;

public class TestClassAnno {
    @MyAnno(priority = 3)
    public static void method1(){
        System.out.println("method 1");
    }
    @MyAnno(priority = 4)
    public static void method2(){
        System.out.println("method 2");
    }
    @MyAnno(priority = 2)
    public static void method3(){
        System.out.println("method 3");
    }
    @MyAnno
    public static void method4(){
        System.out.println("method 4");
    }
    public static void method5(){
        System.out.println("method 5");
    }
}

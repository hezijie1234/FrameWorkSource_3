package com.zijie.reflectdemo.refle.more;

import java.lang.reflect.Method;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class TestMethod {
    /*方法相关*/
    public void testMethod() throws Exception{
        Class clazz = Class.forName("cn.enjoyedu.refle.more.Person");

        System.out.println("获取clazz对应类中的所有方法，" +
                "不能获取private方法,且获取从父类继承来的所有方法");
        Method[] methods = clazz.getMethods();
        for(Method method:methods){
            System.out.print(" "+method.getName()+"()");
        }
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("获取所有方法，包括私有方法，" +
                "所有声明的方法，都可以获取到，且只获取当前类的方法");
        methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            System.out.print(" "+method.getName()+"()");
        }
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("获取指定的方法，" +
                "需要参数名称和参数列表，无参则不需要写");
        //  方法public void setName(String name) {  }
        Method method = clazz.getDeclaredMethod("setName", String.class);
        System.out.println(method);
        System.out.println("---");

        //  方法public void setAge(int age) {  }
        /* 这样写是获取不到的，如果方法的参数类型是int型
        如果方法用于反射，那么要么int类型写成Integer： public void setAge(Integer age) {  }
        要么获取方法的参数写成int.class*/
        method = clazz.getDeclaredMethod("setAge", int.class);
        System.out.println(method);
        System.out.println("---------------------------");


        System.out.println("执行方法，第一个参数表示执行哪个对象的方法" +
                "，剩下的参数是执行方法时需要传入的参数");
        Object obje = clazz.newInstance();
        method.invoke(obje,18);

        /*私有方法的执行，必须在调用invoke之前加上一句method.setAccessible（true）;*/
        method = clazz.getDeclaredMethod("privateMethod");
        System.out.println(method);
        System.out.println("---------------------------");
        System.out.println("执行私有方法");
        method.setAccessible(true);
        method.invoke(obje);
    }
}

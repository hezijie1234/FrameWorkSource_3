package com.zijie.reflectdemo.refle.more;

import java.lang.reflect.Field;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class TestField {
    /*域相关*/
    public void testField() throws Exception{
        String className = "cn.enjoyedu.refle.more.Person";
        Class clazz = Class.forName(className);

        System.out.println("获取公用和私有的所有字段，但不能获取父类字段");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            System.out.print(" "+ field.getName());
        }
        System.out.println();
        System.out.println("---------------------------");


        System.out.println("获取指定字段");
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.getName());

        Person person = new Person("ABC",12);
        System.out.println("获取指定字段的值");
        Object val = field.get(person);
        System.out.println(field.getName()+"="+val);

        System.out.println("设置指定对象指定字段的值");
        field.set(person,"DEF");
        System.out.println(field.getName()+"="+person.getName());

        System.out.println("字段是私有的，不管是读值还是写值，" +
                "都必须先调用setAccessible（true）方法");
        //     比如Person类中，字段name字段是非私有的，age是私有的
        field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        System.out.println(field.get(person));
    }
}

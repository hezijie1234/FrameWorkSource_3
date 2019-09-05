package com.zijie.reflectdemo.refle.more;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class Person {
    String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        System.out.println("this is setName()!");
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
        System.out.println("this is setAge()!");
    }

    //包含一个带参的构造器和一个不带参的构造器
    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }
    public Person() {
        super();
    }

    //私有方法
    private void privateMethod(){
        System.out.println("this is private method!");
    }
}

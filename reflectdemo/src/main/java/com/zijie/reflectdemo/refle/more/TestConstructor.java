package com.zijie.reflectdemo.refle.more;

import java.lang.reflect.Constructor;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class TestConstructor {
    /*构造器相关*/
    public void testConstructor() throws Exception{
        String className = "cn.enjoyedu.refle.more.Person";
        Class<Person> clazz = (Class<Person>) Class.forName(className);

        System.out.println("获取全部Constructor对象-----");
        Constructor<Person>[] constructors
                = (Constructor<Person>[]) clazz.getConstructors();
        for(Constructor<Person> constructor: constructors){
            System.out.println(constructor);
        }


        System.out.println("获取某一个Constructor 对象，需要参数列表----");
        Constructor<Person> constructor
                = clazz.getConstructor(String.class, int.class);
        System.out.println(constructor);

        //2. 调用构造器的 newInstance() 方法创建对象
        System.out.println("调用构造器的 newInstance() 方法创建对象-----");
        Person obj = constructor.newInstance("Mark", 18);
        System.out.println(obj.getName());
    }
}

package com.zijie.reflectdemo.refle;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *往期课程咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示反射的使用
 */
public class RefleDemo {

	public static void main(String[] args) throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException {

	    //实例化对象的标准用法，也就所谓的正
        Servant servant = new Servant();
        servant.service("Hello");

        Class servantClass = Servant.class;
        Class servantClass2 = servant.getClass();
        Class servantClass3 = Class.forName("cn.enjoyedu.refle.Servant");

		Servant servant1 = (Servant)servantClass.newInstance();
		servant1.service("OK");



	}
}

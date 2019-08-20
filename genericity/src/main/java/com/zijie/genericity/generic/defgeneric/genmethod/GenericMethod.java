package com.zijie.genericity.generic.defgeneric.genmethod;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class GenericMethod {

    public <T> T genericMethod(T...a){
        return a[a.length/2];
    }

    public void test(int x,int y){
        System.out.println(x+y);
    }

    public static void main(String[] args) {
        GenericMethod genericMethod = new GenericMethod();
        genericMethod.test(23,343);
        System.out.println(genericMethod.<String>genericMethod("mark","av","lance"));
        System.out.println(genericMethod.genericMethod(12,34,"av"));
    }
}

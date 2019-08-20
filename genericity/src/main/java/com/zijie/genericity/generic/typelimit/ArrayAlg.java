package com.zijie.genericity.generic.typelimit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：类型变量的限定-方法上
 */
public class ArrayAlg {
//    public static <T> T min(T a,T b){
//        if(a.comapareTo(b)>0) return a; else return b;
//    }

    public static <T extends ArrayList&Comparable> T min(T a, T b){
        if(a.compareTo(b)>0) return a; else return b;
    }

    static class Test{}

    public static void main(String[] args) {
        //ArrayAlg.min(new Test(),new Test());
        //TODO

    }
}

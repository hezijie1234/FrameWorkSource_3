package com.zijie.genericity.generic.theory;

import java.util.ArrayList;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class GenericRaw<T extends ArrayList&Comparable> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void test(){
        //(Comparable)data.compareTo()
    }


    public static void main(String[] args) {

    }
}

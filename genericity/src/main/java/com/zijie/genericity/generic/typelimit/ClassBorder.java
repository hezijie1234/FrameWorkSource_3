package com.zijie.genericity.generic.typelimit;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：类型变量的限定-类上
 */
public class ClassBorder<T extends Comparable> {
    private T data;

    public T min(T outter){
        if(this.data.compareTo(outter)>0)
            return outter;
        else
            return this.data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        ClassBorder<String> classBorder = new ClassBorder<>();
        classBorder.setData("mark");
        System.out.println(classBorder.min("av"));
    }
}

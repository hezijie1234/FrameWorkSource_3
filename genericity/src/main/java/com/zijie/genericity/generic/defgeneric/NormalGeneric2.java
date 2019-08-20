package com.zijie.genericity.generic.defgeneric;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：泛型类
 */
public class NormalGeneric2<T,K> {
    private T data;
    private K result;

    public NormalGeneric2() {
    }

    public NormalGeneric2(T data) {
        this();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NormalGeneric2(T data, K result) {
        this.data = data;
        this.result = result;
    }

    public K getResult() {
        return result;
    }

    public void setResult(K result) {
        this.result = result;
    }

    public static void main(String[] args) {
        NormalGeneric2<String,Integer> normalGeneric2 = new NormalGeneric2<>();
        normalGeneric2.setData("OK");
        System.out.println(normalGeneric2.getData());
        normalGeneric2.setResult(23);
        System.out.println(normalGeneric2.getResult());

    }
}

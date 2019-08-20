package com.zijie.genericity.generic.inherit;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class Pair<T> {

    private T one;
    private T two;

    public T getOne() {
        return one;
    }

    public void setOne(T one) {
        this.one = one;
    }

    public T getTwo() {
        return two;
    }

    public void setTwo(T two) {
        this.two = two;
    }

    private static <T> void set(Pair<Employee> p){
    }

    public static void main(String[] args) {
        //Pair<Employee>和Pair<Worker>没有任何继承关系
        Pair<Employee> employeePair = new Pair<>();
        Pair<Worker> workerPair = new Pair<>();

        Employee employee = new Worker();
        //Pair<Employee> employeePair2 = new Pair<Worker>();


        Pair<Employee> pair = new ExtendPair<>();


        set(employeePair);
        //set(workerPair);
    }

    /*泛型类可以继承或者扩展其他泛型类，比如List和ArrayList*/
    private static class ExtendPair<T> extends Pair<T>{

    }
}

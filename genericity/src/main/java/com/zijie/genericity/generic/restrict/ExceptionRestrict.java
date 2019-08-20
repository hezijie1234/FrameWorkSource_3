package com.zijie.genericity.generic.restrict;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：泛型和异常
 */
public class ExceptionRestrict {

    /*泛型类不能extends Exception/Throwable*/
    //private class Problem<T> extends Exception;

    /*不能捕获泛型类对象*/
//    public <T extends Throwable> void doWork(T x){
//        try{
//
//        }catch(T x){
//            //do sth;
//        }
//    }


    public <T extends Throwable> void doWorkSuccess(T x) throws T{
        try{

        }catch(Throwable e){
            throw x;
        }
    }
}

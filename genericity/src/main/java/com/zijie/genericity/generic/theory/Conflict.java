package com.zijie.genericity.generic.theory;

import java.util.List;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class Conflict {

    public static String method(List<String> stringList){
        System.out.println("List");
        return "OK";
    }

//    public static Integer method(List<Integer> stringList){
//        System.out.println("List");
//        return 1;
//    }

    /*
    * Signature (弱记忆)
    *
    * ? super xxxx
    *
    * */

}

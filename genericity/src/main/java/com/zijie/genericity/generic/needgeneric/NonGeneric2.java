package com.zijie.genericity.generic.needgeneric;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class NonGeneric2 {
    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("mark");
        list.add("OK");
        //list.add(100);

        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i); // 1
            System.out.println("name:" + name);
        }
    }
}

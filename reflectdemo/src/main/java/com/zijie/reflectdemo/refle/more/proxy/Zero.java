package com.zijie.reflectdemo.refle.more.proxy;

/**
 * 被委托类
 */
public class Zero implements IShop {
    @Override
    public void buy() {
        System.out.println("购买..." );
    }
}

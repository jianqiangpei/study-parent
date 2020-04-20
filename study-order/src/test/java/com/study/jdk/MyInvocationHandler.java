package com.study.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    /**
     * 代理对象
     */
    private final Object targer;

    public MyInvocationHandler(Object targer){
        this.targer = targer;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("-------------------前置增强通知-----------------");

        Object rs = method.invoke(targer , args);


        System.out.println("-------------------后置增强通知-----------------");


        return rs;
    }
}

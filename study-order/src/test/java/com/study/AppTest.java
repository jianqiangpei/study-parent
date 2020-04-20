package com.study;

import static org.junit.Assert.assertTrue;

import com.study.cglib.StudyMethodInterceptor;
import com.study.cglib.TestService;
import com.study.jdk.HelloImpl;
import com.study.jdk.IHello;
import com.study.jdk.MyInvocationHandler;
import org.junit.Test;
import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

        IHello hello = (IHello)Proxy.newProxyInstance(IHello.class.getClassLoader() , new Class[]{IHello.class} , new MyInvocationHandler(new HelloImpl()));

        hello.sayHello();

    }

    @Test
    public void cjlibTest(){

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestService.class);
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setCallback(new StudyMethodInterceptor());

        TestService testService = (TestService)enhancer.create();
        testService.test();
    }
}

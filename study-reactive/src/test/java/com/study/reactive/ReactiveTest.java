package com.study.reactive;

import org.junit.Test;
import rx.Observable;

public class ReactiveTest {

    @Test
    public void test1(){


        String arrays [] = new String [] {"阿里" , "字节" , "腾讯" , "美团" , "滴滴" , "快手"};

        Observable<String> observable = Observable.from(arrays)
                .doOnNext(System.out :: println);

        System.out.println("12345");

        observable.subscribe();

        System.out.println("45678");

    }



}

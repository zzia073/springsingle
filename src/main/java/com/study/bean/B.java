package com.study.bean;

/**
 * @author ：fei
 * @date ：Created in 2019/10/28 0028 10:54
 */
public class B extends A {
    static {
        System.out.println("B is init!");
    }
    public A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}

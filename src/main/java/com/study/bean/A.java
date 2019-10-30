package com.study.bean;

/**
 * @author ：fei
 * @date ：Created in 2019/10/28 0028 10:54
 */
public class A {
    public static String AC = "AC";
    public B b;
    static {
        System.out.println("A is init!");
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}

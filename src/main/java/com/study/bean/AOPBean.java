package com.study.bean;

/**
 * @author ：fei
 * @date ：Created in 2019/10/24 0024 09:43
 */
public class AOPBean {
    public static void main(String[] args) {
        System.out.println(B.AC);
    }
    public void print(){
        System.out.println("print proceed");
    }
}

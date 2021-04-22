package com.test;

public class StringLength {
    public static void main(String[] args) {
        // String s = "1\t2\t3\t4";
        String s = "1       2       3       4";
        System.out.println(s.replace("\t", ","));
        System.out.println(s.length());
    }
}
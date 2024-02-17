package com.pg.main.util;


import com.pg.main.annotations.PGAnnotation;

public class Utils {
    private static Utils obj;

    private Utils(){}

    public static Utils getInstance(){
        if(obj==null){
            obj = new Utils();
        }
        return obj;
    }

    @PGAnnotation(name = "Hello, World!", role = "Software Developer")
    public int sum(int a, int b){
        return a+b;
    }

    public int minus(int a, int b){
        return a-b;
    }
}

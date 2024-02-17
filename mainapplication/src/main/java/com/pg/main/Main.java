package com.pg.main;

import com.pg.main.model.EmployeeBuilder;
import com.pg.main.annotations.PGAnnotation;
import com.pg.main.model.Employee;
import com.pg.main.util.Utils;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        /*
        * In below code using Java Reflection API, to check custom annotation @PGAnnotation and fetching data from it.
        * */
        Class classObj = Utils.class;
        for(Method method : classObj.getMethods()){
            PGAnnotation pgAnnotation = (PGAnnotation)method.getAnnotation(PGAnnotation.class);
            if(pgAnnotation!=null){
                System.out.println(pgAnnotation.toString()+" found on "+method.getName());
                System.out.println("Name : "+pgAnnotation.name());
                System.out.println("Role : "+pgAnnotation.role());
            }
        }

        /*
        * Here EmployeeBuilder class generated using annotation processor during compile time.
        * */
        Employee emp = new EmployeeBuilder().id(1234).name("Prateek").build();
        System.out.println(emp);
    }
}

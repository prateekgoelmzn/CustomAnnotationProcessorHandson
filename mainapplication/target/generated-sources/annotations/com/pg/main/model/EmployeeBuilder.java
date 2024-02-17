package com.pg.main.model;
public class EmployeeBuilder{
private int id;
private java.lang.String name;
public EmployeeBuilder id(int value){
id = value;
return this;
}
public EmployeeBuilder name(java.lang.String value){
name = value;
return this;
}
public Employee build(){
return new Employee(id, name);
}
}

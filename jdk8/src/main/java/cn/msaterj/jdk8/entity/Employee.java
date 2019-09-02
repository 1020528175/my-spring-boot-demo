package cn.msaterj.jdk8.entity;

import lombok.Data;

@Data
public class Employee {

    private int id;
    private String name;
    private int age;
    private Double salary;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Employee(int id, String name, int age, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String show() {
        return "测试方法引用！";
    }


}

package cn.msaterj.jdk8;

import cn.msaterj.jdk8.entity.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author masterJ
 * @create 2019-09-02 20:05
 */
public class LambdaTest {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    @Test
    public void test1(){
       Collections.sort(emps,(emp1,emp2)-> -Integer.compare(emp1.getAge(),emp2.getAge()));
        System.out.println("emps = " + emps);
        Collections.sort(emps, Comparator.comparing(Employee::getSalary));
        System.out.println("emps = " + emps);
    }

}

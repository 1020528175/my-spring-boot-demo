package cn.msaterj.jdk8;

import cn.msaterj.jdk8.entity.Employee;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author masterJ
 * @create 2019-09-02 20:27
 */
public class StreamAPITest {

    List<Employee> employees = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    @Test
    public void test1() {
        //filter(Predicate p) 接收 Lambda ，从流中排除某些元素。
        //distinct() 筛选，通过流所生成元素的 hashCode () 和 equals () 去除重复元素
        //limit( long maxSize)截断流，使其元素不超过给定数量。
        //skip( long n)跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit (n) 互补
        employees.stream().filter((e1) ->e1.getAge() >= 28).distinct().limit(2).forEach(System.out::println);
        
        
        //findFirst() 返回第一个元素
        //findAny() 返回当前流中的任意元素
        Optional<Employee> optional = employees.stream().filter(employee -> employee.getSalary() > 5000).findAny();
        System.out.println("optional.get() = " + optional.get());

    }

    @Test
    public void test2(){
        //map(Function f) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        //mapToDouble(ToDoubleFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
        //mapToInt(ToIntFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
        //mapToLong(ToLongFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
        //flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流

        employees.stream().mapToInt(Employee::getAge).forEach(System.out::print);

        employees.stream().map(employee -> {
            employee.setAge(employee.getAge() + 1);
            return employee;
        }).forEach(System.out::println);

        //map和flatMap的区别,类似于机会的 add和addAll的区别,flatMap会将流合并成一个新流
        Stream<Stream<String>> streamStream = employees.stream().map(employee -> Arrays.stream(employee.getName().split(",")));
        List<Stream<String>> collect1 = streamStream.collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);//collect1 = [java.util.stream.ReferencePipeline$Head@306279ee, java.util.stream.ReferencePipeline$Head@545997b1, java.util.stream.ReferencePipeline$Head@4cf4d528, java.util.stream.ReferencePipeline$Head@77846d2c, java.util.stream.ReferencePipeline$Head@548ad73b, java.util.stream.ReferencePipeline$Head@4c762604, java.util.stream.ReferencePipeline$Head@2641e737]

        Stream<String> stringStream = employees.stream().flatMap(employee -> {
            return Arrays.stream(employee.getName().split(","));
        });
        List<String> collect = stringStream.collect(Collectors.toList());
        System.out.println("collect = " + collect);//collect = [李四, 张三, 王五, 赵六, 赵六, 赵六, 田七]

    }

    @Test
    public void test3(){
        //sorted() 产生一个新流，其中按自然顺序排序
        //sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序
        //allMatch(Predicate p) 检查是否匹配所有元素
        //anyMatch(Predicate p) 检查是否至少匹配一个元素
        //noneMatch(Predicate p) 检查是否没有匹配所有元素
        employees.stream().sorted(Comparator.comparing(Employee::getAge)).forEach(System.out::println);

        System.out.println(employees.stream().allMatch(employee -> employee.getAge() == 8));//false

        System.out.println(employees.stream().anyMatch(employee -> employee.getAge() == 8));//true
    }

    @Test
    public void test4(){
        //count() 返回流中元素总数
        //max(Comparator c) 返回流中最大值
        //min(Comparator c) 返回流中最小值
        System.out.println(employees.stream().count());

        System.out.println(employees.stream().max(Comparator.comparing(Employee::getAge)).get());

        System.out.println(employees.stream().filter(employee -> employee.getAge() > 30).min(Comparator.comparing(Employee::getSalary)).get());
    }


    @Test
    public void test5(){
        //归约
        //reduce(T iden, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。 返回 T
        //reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。 返回 Optional<T>

        //取最大值
        System.out.println(employees.stream().map(Employee::getSalary).reduce(0.0, BinaryOperator.maxBy(Double::compareTo)).doubleValue());

        System.out.println(employees.stream().map(Employee::getSalary).reduce((x, y) -> x + y).get());
        System.out.println(employees.stream().map(Employee::getSalary).reduce((x, y) -> x * y).get());
    }

    @Test
    public void test6(){
        //收集
        //collect(Collector c) 将流转换为其他形式。接收一个 Collector接口实现，用于给Stream中元素做汇总的方法
        List<Employee> collect = employees.stream().filter(employee -> employee.getAge() > 20).collect(Collectors.toList());
        System.out.println("collect = " + collect);

        ArrayList<Employee> collect1 = employees.stream().filter(employee -> employee.getSalary() > 5000).collect(Collectors.toCollection(ArrayList::new));
        System.out.println("collect1 = " + collect1);

        //平均值
        System.out.println(employees.stream().collect(Collectors.averagingDouble(Employee::getSalary)));


        Map<Integer, Map<Double, List<Employee>>> collect2 = employees.stream().collect(Collectors.groupingBy(Employee::getAge, Collectors.groupingBy(Employee::getSalary)));
        System.out.println(collect2);

        Map<Integer, Long> collect3 = employees.stream().collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));
        System.out.println("collect3 = " + collect3);

        Map<String, List<Employee>> collect4 = employees.stream().collect(Collectors.groupingBy(employee -> {
            if (employee.getAge() > 50) {
                return "老年";
            } else if (employee.getAge() < 30) {
                return "青年";
            } else {
                return "中年";
            }
        }));
        System.out.println("collect4 = " + collect4);

        Map<Boolean, List<Employee>> collect5 = employees.stream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 50));

        System.out.println("collect5 = " + collect5);

        //多种运算结果于一体：DoubleSummaryStatistics{count=7, sum=48888.840000, min=3333.330000, average=6984.120000, max=9999.990000}
        DoubleSummaryStatistics doubleSummaryStatistics = employees.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("doubleSummaryStatistics = " + doubleSummaryStatistics);
        System.out.println("doubleSummaryStatistics.getSum() = " + doubleSummaryStatistics.getSum());

        //单独只求和：48888.840000000004
        System.out.println(employees.parallelStream().collect(Collectors.summingDouble(Employee::getSalary)));

        //连接
        String collect6 = employees.parallelStream().map(Employee::getName).collect(Collectors.joining(",","","----------"));
        System.out.println("collect6 = " + collect6);

    }
}

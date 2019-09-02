package cn.masterj.mongodbdemo.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-08-08 14:55
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {
    private String id;
    private String userName;
    private Integer age;
    /**
     * 入职时间
     */
    @Field("entry_date")
    private Date entryDate;
    /**
     * 股份
     */
    private Double share;

    private List<Employee> employees;

    public Employee(String id, String userName, Integer age, Date entryDate, Double share) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.entryDate = entryDate;
        this.share = share;
    }
}

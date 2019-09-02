package cn.masterj.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author shkstart
 * @create 2019-08-23 10:24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Long id;
    private String userName;
    private Integer age;
    private String email;
    @TableField(exist = false)
    private BigDecimal salary;



}

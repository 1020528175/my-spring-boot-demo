package cn.masterj.mongodbdemo.service;

import cn.masterj.mongodbdemo.entity.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author java
 * @create 2019-08-21 15:46
 */
public interface IEmployeeService {

    Employee insert(Employee employee);

    Employee save(Employee employee);

    void delete(String id);

    Employee get(String id);

    List<Employee> getByAge(Integer age);

    List<Employee> findAll();
}

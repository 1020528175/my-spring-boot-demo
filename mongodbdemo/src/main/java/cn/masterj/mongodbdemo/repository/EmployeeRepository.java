package cn.masterj.mongodbdemo.repository;

import cn.masterj.mongodbdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author shkstart
 * @create 2019-08-21 15:44
 */
public interface EmployeeRepository extends MongoRepository<Employee,String> {

    List<Employee> getByAge(Integer age);

    Page<Employee> findByAge(Integer age, Pageable pageable);

}

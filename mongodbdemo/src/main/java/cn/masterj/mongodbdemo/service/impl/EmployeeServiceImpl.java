package cn.masterj.mongodbdemo.service.impl;

import cn.masterj.mongodbdemo.entity.Employee;
import cn.masterj.mongodbdemo.repository.EmployeeRepository;
import cn.masterj.mongodbdemo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2019-08-21 15:54
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public Employee insert(Employee employee) {
        return employeeRepository.insert(employee);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee get(String id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public List<Employee> getByAge(Integer age) {
        Pageable pageable = PageRequest.of(0,2,Sort.by("share").descending());
        Page<Employee> page = employeeRepository.findByAge(age,pageable);
        return page.getContent();
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


}

package cn.masterj.mongodbdemo.controller;

import cn.masterj.mongodbdemo.entity.Employee;
import cn.masterj.mongodbdemo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-08-21 16:00
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/insert")
    public Employee insert(@RequestBody Employee employee) {
        employee.setEntryDate(new Date());
        return employeeService.insert(employee);
    }

    @PutMapping("/save")
    public Employee save(@RequestBody Employee employee) {
        if (employee.getId() == null){
            employee.setEntryDate(new Date());
        }
        return employeeService.save(employee);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        employeeService.delete(id);
    }

    @GetMapping("/get/{id}")
    public Employee get(@PathVariable("id") String id) {
        return employeeService.get(id);
    }

    @GetMapping("/getByAge/{age}")
    public List<Employee> getByAge(@PathVariable("age") Integer age) {
        return employeeService.getByAge(age);
    }

    @GetMapping("/findAll")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

}

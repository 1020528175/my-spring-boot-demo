package cn.masterj.mybatisplus;

import cn.masterj.mybatisplus.entity.Employee;
import cn.masterj.mybatisplus.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-08-23 10:23
 */
public class TestMybatisPlus extends MybatisplusApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void testInsert(){
        System.out.println("employeeMapper = " + employeeMapper);
        Employee employee = new Employee(null,"王五",33,"wangwu@qq.com",new BigDecimal(15.2));
        int insert = employeeMapper.insert(employee);
        System.out.println("insert = " + insert);
    }

    @Test
    public void testDel(){
        employeeMapper.delete(new QueryWrapper<Employee>().eq("user_name","张三"));
    }

    @Test
    public void testSeletct(){
        Employee employee = new Employee();
        employee.setAge(33);
        Integer integer = employeeMapper.selectCount(new QueryWrapper<>(employee));
        System.out.println("integer = " + integer);

//        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>().eq("age", 11));
        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>().lambda().eq(Employee::getUserName, "张三"));

        System.out.println("employeeList = " + employeeList);

        //这样分页并没有什么卵用，还是全部查出来了
        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<>(1, 2), new QueryWrapper<Employee>().eq("user_name","张三").notExists(true, "select * from tab_employee where age = 11"));
//        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<>(1, 2), null);
        System.out.println("employeeIPage = " + employeeIPage.getRecords());
        System.out.println("employeeIPage = " + employeeIPage.getCurrent());
        System.out.println("employeeIPage = " + employeeIPage.getPages());
        System.out.println("employeeIPage = " + employeeIPage.getSize());

    }



    @Test
    public void testUpdate(){
        Employee employee = new Employee(4L,"赵六",33,"zhaoliu@qq.com",new BigDecimal(15.2));

        //没条件会修改全部数据
//        int update = employeeMapper.update(employee, null);
//        System.out.println("update = " + update);

//        int update1 = employeeMapper.update(employee, new QueryWrapper<Employee>().eq("id",4));
        //UPDATE tab_employee  SET user_name=?, age=?, email=?,  id=?   ;set("id",4) 转化成sql就是 在sql后面接上,id=?,显然在这里是不行的
//        int update1 = employeeMapper.update(employee, new UpdateWrapper<Employee>().set("id",4));
        int update1 = employeeMapper.update(employee, new UpdateWrapper<Employee>().eq("id",4));
        System.out.println("update1 = " + update1);
    }


}

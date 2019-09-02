package cn.masterj.mybatisplus.user.controller;


import cn.masterj.mybatisplus.user.entity.User;
import cn.masterj.mybatisplus.user.mapper.UserMapper;
import cn.masterj.mybatisplus.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.masterj.mybatisplus.controller.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author master
 * @since 2019-08-23
 */
@RestController
@RequestMapping("/user/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("findAll")
    public List<User> findAll(){
        return userService.list();
    }

    @GetMapping("findByLastName")
    public List<User> findByLastName(){
        return userService.findByLastName("Billie");
    }

    @GetMapping("findPage")
    public List<User> findPage(){
        return userService.findPage(new Page(1,2),new QueryWrapper<User>().gt("age",20).le(true,"age",24));
    }


    @GetMapping("findPageByLastName")
    public List<User> findPageByLastName(String lastName){
        return userService.findPageByLastName(new Page(1,2),lastName);
    }

    @PostMapping("insert")
    public boolean insert(@RequestBody User user){
        return userService.save(user);
    }
}

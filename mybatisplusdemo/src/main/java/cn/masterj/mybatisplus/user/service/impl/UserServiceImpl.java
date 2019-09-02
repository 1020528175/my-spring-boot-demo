package cn.masterj.mybatisplus.user.service.impl;

import cn.masterj.mybatisplus.user.entity.User;
import cn.masterj.mybatisplus.user.mapper.UserMapper;
import cn.masterj.mybatisplus.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author master
 * @since 2019-08-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     *  在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
     *  这只是记录下这个注解，查询方法是不需要加的
     * @param lastName
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public List<User> findByLastName(String lastName) {
        return baseMapper.findByLastName(lastName);
    }

    @Override
    public List<User> findPage(Page page,QueryWrapper queryWrapper) {
        IPage<User> userIPage = baseMapper.selectPage(page,queryWrapper);
        return userIPage.getRecords();
    }

    @Override
    public List<User> findPageByLastName(IPage page, String lastName) {
        return baseMapper.findPageByLastName(page,"%" + lastName + "%");
    }


}

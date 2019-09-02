package cn.masterj.mybatisplus.user.service;

import cn.masterj.mybatisplus.user.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author master
 * @since 2019-08-23
 */
public interface IUserService extends IService<User> {

    /**
     *
     * @param lastName
     * @return
     */
    List<User> findByLastName(String lastName);

    List<User> findPage(Page page, QueryWrapper queryWrapper);

    List<User> findPageByLastName(IPage page, String lastName);
}

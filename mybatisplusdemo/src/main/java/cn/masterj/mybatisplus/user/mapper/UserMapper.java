package cn.masterj.mybatisplus.user.mapper;

import cn.masterj.mybatisplus.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author master
 * @since 2019-08-23
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> findByLastName(String lastName);

    List<User> findPageByLastName(IPage page,String lastName);

    List<User> findAllByLastName(@Param("lastName") String lastName);

    void insertUser(User user);
}

package cn.masterj.mybatisplus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("cn.masterj.mybatisplus.mapper")
public class MybatisplusApplicationTests {

    @Test
    public void contextLoads() {
    }

}

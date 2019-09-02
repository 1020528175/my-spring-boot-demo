package cn.masterj.mybatisplus;

import cn.masterj.mybatisplus.user.service.IUserService;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.*;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    @Test
    public void test1(){
        System.out.println(System.getProperty("sys.dir"));
    }


    @Test
    public void testAutoGenerator() {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();


        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //main方法获得项目路径    D:\java_work\workspace\example\java\demo
        //test方法获得项目路径    D:\java_work\workspace\example\java\demo\mybatisplusdemo
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath = " + projectPath);
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("master");
        globalConfig.setOpen(false);
        globalConfig.setIdType(IdType.AUTO);
        //mapper的xml文件生成基础的结果集映射
        globalConfig.setBaseResultMap(true);
        //mapper的xml文件生成所有列的sql
        globalConfig.setBaseColumnList(true);
        // globalConfig.setSwagger2(true); 实体属性 Swagger2 注解
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://192.168.1.101:3306/mp?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dataSourceConfig.setSchemaName("public");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("mybatisplus");
        dataSourceConfig.setPassword("mybatisplus");
        autoGenerator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        //配置模块名--实际就是父包下再加一层包名
        packageConfig.setModuleName("user");
        packageConfig.setParent("cn.masterj.mybatisplus");
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
//                Map<String, Object> map = new HashMap<>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
            }
        };
        Map<String, Object> map = new HashMap<>();
        map.put("userService", IUserService.class);
        injectionConfig.setMap(map);

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
//         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出  这里指定的是使用templatePath的mapper.xml模板  生成%smapper.xml的生成路径
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        //覆盖原来的文件
        injectionConfig.setFileCreate((ConfigBuilder configBuilder, FileType fileType, String filePath) -> true);

        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别，默认的模板路径是在mybatis-plus-generator-3.1.2.jar的templates下面,这里可以指定使用自定义的模板
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        //mapper xml 模板
        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

//        strategy.setSuperEntityClass("cn.masterj.mybatisplus.common.BaseEntity");
        // 写于父类中的公共字段,entity没有父类就不用配置这项，这项就是把这个字段抽到父类中
//        strategy.setSuperEntityColumns("id");
        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("cn.masterj.mybatisplus.controller.BaseController");
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        strategy.setInclude("tab_user");
        strategy.setTablePrefix("tab_");
        autoGenerator.setStrategy(strategy);


        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

}
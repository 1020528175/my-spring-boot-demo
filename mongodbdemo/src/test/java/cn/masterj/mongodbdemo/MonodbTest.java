package cn.masterj.mongodbdemo;

import cn.masterj.mongodbdemo.entity.Employee;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

/**
 * @author shkstart
 * @create 2019-08-08 14:40
 */
public class MonodbTest extends MongodbdemoApplicationTests{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test(){
        MongoClient mongoClient = new MongoClient("192.168.1.101", 27017);
        MongoDatabase demo_test = mongoClient.getDatabase("demo_test");
        System.out.println("demo_test = " + demo_test);
        demo_test.createCollection("employee");
    }

    @Test
    public void testSave(){
        System.out.println("///");
        System.out.println("mongoTemplate = " + mongoTemplate);
        Employee employee = new Employee("5d4cdf4cbbbdcd2b4c0ec55d","张三",11,new Date(),0.32);
        System.out.println("employee = " + employee);
        //save  就是 id为空就是插入，id不为空就是修改
        Employee employee1 = mongoTemplate.save(employee);
        List list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(new Employee(null,"李" + i,i + 20,new Date(),i * 0.01));
        }
        mongoTemplate.insertAll(list);
        System.out.println("employee1 = " + employee1);
    }


    @Test
    public void testQuery(){
        List<Employee> employees = mongoTemplate.findAll(Employee.class);
        System.out.println("employees = " + employees);
        System.out.println(mongoTemplate.findById("5d4ce1a4bbbdcd3a44fd519b", Employee.class));

        System.out.println(mongoTemplate.findOne(Query.query(Criteria.where("userName").is("李4")), Employee.class));

        System.out.println(mongoTemplate.find(Query.query(Criteria.where("age").gt(11).lt(30)).with(Sort.by("age").ascending()), Employee.class));

        System.out.println(mongoTemplate.find(Query.query(Criteria.where("share").gt(0.01).lt(0.04).and("entryDate").lt(new Date())).limit(1).skip(1), Employee.class));

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("entryDate").sum("age").as("totalAge"));
        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, "employee", Document.class);
//        System.out.println(aggregate.getMappedResults());
        Date date = new Date();
        for (Document e : aggregate.getMappedResults()) {
            System.out.println(e);
            System.out.println(e.get("totalAge"));
            System.out.println(e.get("_id"));
            System.out.println(date.equals(e.get("_id")));
            System.out.println(((Date) e.get("_id")).getTime());
            date = (Date) e.get("_id");
        }
        AggregationResults<Map> aggregate1 = mongoTemplate.aggregate(aggregation, "employee", Map.class);
        System.out.println(aggregate1.getMappedResults());
    }

    @Test
    public void testBasicQuery(){
        //超级简单的查询 就可以直接这样
        Document queryDocument = new Document("age",11);

//        QueryBuilder queryBuilder = new QueryBuilder();
        QueryBuilder queryBuilder = QueryBuilder.start("age").greaterThan(10).and(new BasicDBObject("userName","张三"));

        //用这种方式就可以指定只返回哪些字段，默认会返回主键id的
        Document fieldDocument = new Document("age","返回指定的字段，只在于key，这个value可以随便写");
        fieldDocument.put("share","乱写");//要返回哪些字段就可以增加哪些字段，也可以直接创建对象时传入一个map
//        Query query = new BasicQuery(queryDocument,fieldDocument);
        Query query = new BasicQuery(queryBuilder.get().toString(),fieldDocument.toJson());//  DBObject.toString == public String toString() {return this.toJson();}

        System.out.println(mongoTemplate.findOne(query, Document.class,"employee"));
        System.out.println(mongoTemplate.find(query, Map.class,"employee"));

    }

    @Test
    public void testDel(){
        Employee employee = new Employee("5d4bd8a2bbbdcd1e80c62e01","张三",16,new Date(),0.32);
        DeleteResult deleteResult = mongoTemplate.remove(employee);
        System.out.println("deleteResult = " + deleteResult);
        System.out.println("deleteResult.getDeletedCount() = " + deleteResult.getDeletedCount());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("5d4bd8cbbbbdcd1c34003b1a"));
        mongoTemplate.remove(query, "employee");
    }

    @Test
    public void testUpdate(){
        Employee employee = new Employee("5d4cdf4cbbbdcd2b4c0ec55d","李四",null,new Date(),1d);
//        String jsonString = JSONObject.toJSONString(employee, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        String jsonString = JSONObject.toJSONString(employee);
        System.out.println("jsonString = " + jsonString);
//        这种转换成map后，字段的格式会出错,时间在上一步，时间格式变成json，格式就已经产生了变化
        Document document = Document.parse(jsonString);
        Update update = Update.fromDocument(document);
//        mongoTemplate.updateMulti(Query.query(Criteria.where("id").is("5d4cdf4cbbbdcd2b4c0ec55d")),update,Employee.class);

        //晕死，研究了半天，想一次性修改一个对象的多个字段，结果发现字段类型如果是时间类型的话，时间就会不对，
        //采用这种方式就解决了，如果主键id的document没有就是insert，如果有就是update，
        //但是这个如果是修改的话得确保每个字段的值是你想修改的，因为这个会修改每个字段的值，空值也会修改
        mongoTemplate.save(employee);
    }


    @Test
    public void testUpdate1(){
        Employee employee = new Employee("5d4cdf4cbbbdcd2b4c0ec55d","李四",22,new Date(),0.11);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("5d4bd8cbbbbdcd1c34003b1a"));
        //update，set 如果没有就会增加一个字段，push如果没有也会增加一个字段，但字段的值是数组，如果有就是往数组里面增加元素[1, 1, 1]
//        Update update = Update.update("userName1","王五").set("age",11).push("a",1);

//        Employee employee1 = new Employee();
//        BeanUtils.copyProperties(employee,employee1);
//        System.out.println("employee1 = " + employee1);

        Map map = new HashMap();
        //这种转换成map后，字段的格式会出错
//        map = (Map) JSONObject.parse(JSONObject.toJSON(employee).toString());
//        map.put("entryDate",new Date());
//        System.out.println("map = " + map);
//        Document document = new Document(map);
//        Update update = Update.fromDocument(document);
//        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Employee.class);
//        System.out.println("updateResult = " + updateResult);

    }


//    ===========================================================包含多级========================================================================================

    /**
     * 多级保存
     */
    @Test
    public void testMultistageSave(){
        Employee employee = new Employee("5d4cdf4cbbbdcd2b4c0ec55d","张三",11,new Date(),0.32);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("5d4cdf4cbbbdcd2b4c0ec551","张三1",33,new Date(),0.12));
        employee.setEmployees(employees);
        //save 就会把父和子一起修改了，没有就是添加,子对象主键id为null的话，默认就不会保存id字段，如果有值就会保存
//        mongoTemplate.save(employee);

        System.out.println(mongoTemplate.findById("5d4cdf4cbbbdcd2b4c0ec55d", Employee.class));
    }


    @Test
    public void testMultistageQuery(){
        //elemMatch 增加匹配子元素的条件
        Query query = Query.query(Criteria.where("employees").elemMatch(Criteria.where("userName").is("张三1"))).with(Sort.by(Sort.Order.desc("entry_date")));
//        [Employee(id=5d4cdf4cbbbdcd2b4c0ec55d, userName=张三, age=11, entryDate=Fri Aug 09 16:30:40 CST 2019, share=0.32, employees=[Employee(id=5d4cdf4cbbbdcd2b4c0ec551, userName=张三1, age=33, entryDate=Fri Aug 09 16:30:40 CST 2019, share=0.12, employees=null)])]
        System.out.println(mongoTemplate.find(query, Employee.class));
    }

    @Test
    public void testMultistageUpdate(){
        Employee employee = new Employee("5d4cdf4cbbbdcd2b4c0ec552","张三2",44,new Date(),0.42);
        Update update = new Update().push("employees",employee);
        //upsert  有就修改，没有就增加，但是上面Update使用push的话，下面的upsert，updateMulti等就都只是增加了
        UpdateResult updateResult = mongoTemplate.updateMulti(Query.query(Criteria.where("id").is("5d4cdf4cbbbdcd2b4c0ec55d")), update, Employee.class);
        System.out.println("updateResult = " + updateResult);
    }

    @Test
    public void testMultistageSonUpdate(){
        Employee employee = new Employee("5d4cdf4cbbbdcd2b4c0ec552","张三1-1",110,new Date(),0.42);
        Update update = new Update().push("employees.$.employees",employee);
        //upsert  有就修改，没有就增加，但是上面Update使用push的话，下面的upsert，updateMulti等就都只是增加了
        UpdateResult updateResult = mongoTemplate.updateMulti(Query.query(Criteria.where("employees").elemMatch(Criteria.where("id").is("5d4cdf4cbbbdcd2b4c0ec551"))), update, Employee.class);
        System.out.println("updateResult = " + updateResult);
    }


}

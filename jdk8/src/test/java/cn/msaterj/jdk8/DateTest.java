package cn.msaterj.jdk8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author masterJ
 * @create 2019-09-02 22:03
 */
public class DateTest {


    @Test
    public void test1(){
        //Instant 给电脑看的时间
        Instant now = Instant.now();
        System.out.println("now = " + now);     //2019-09-02T14:15:12.256Z
        
        Instant now1 = Instant.now(Clock.systemDefaultZone());
        System.out.println("now1 = " + now1);   //2019-09-02T14:15:12.349Z
        Instant now2 = Instant.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(8)));
        System.out.println("now2 = " + now2);   //2019-09-02T22:15:12.349Z
        System.out.println(now2.toEpochMilli()); //1567433953238

        now = now.atOffset(ZoneOffset.ofHours(8)).toInstant(); //2019-09-02T22:15:12.349Z
        System.out.println(now.plus(1, ChronoUnit.DAYS));
//        System.out.println("now.plus(1,ChronoUnit.YEARS) = " + now.plus(1,ChronoUnit.YEARS));//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Years

        System.out.println(Duration.between(now, now2).getSeconds());//相差多少
    }

    @Test
    public void test2(){
        //LocalDate 年月日 LocalTime 时分秒  LocalDateTime 年月日时分秒  给人看的时间
        System.out.println(LocalDateTime.of(2019, 9, 1, 11, 11, 11));
        LocalDateTime localDateTime = LocalDateTime.now().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(localDateTime);
        System.out.println(localDateTime.minusMonths(1));
        System.out.println(localDateTime.plusMonths(1));
        System.out.println(localDateTime.plusWeeks(1));
    }

    @Test
    public void test3(){
        //DateTimeFormatter  格式化时间对象
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        System.out.println(dateTimeFormatter.format(LocalDateTime.now().atOffset(ZoneOffset.ofHours(8))));
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTimeFormatter1.format(LocalDateTime.now().atOffset(ZoneOffset.ofHours(8))));

    }


}

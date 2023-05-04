package org.zhang.mistakes.datetime;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/21 16:08
 *
 *
 * 利用 new Date() 初始化的问题
 */
public class DateInitialization {

    public static void main(String[] args) {
        wrong();
        right();
        better();
    }

    /**
     * 参数认知错误
     */
    public static void wrong() {
        System.out.println("wrong");
        // Sat Jan 31 11:12:13 CST 3920
        Date date = new Date(2019, 12, 31, 11, 12, 13);
        System.out.println(date);
    }

    /**
     * 硬修复
     */
    public static void right1() {
        System.out.println("right");
        Date date = new Date(2019 - 1900, 11, 31, 11, 12, 13);
        System.out.println(date);
    }


    /**
     * 正确的方式
     */
    public static void right() {
        System.out.println("right");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 11, 31, 11, 12, 13);
        System.out.println(calendar.getTime());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar2.set(2019, Calendar.DECEMBER, 31, 11, 12, 13);
        System.out.println(calendar2.getTime());
    }

    /**
     * java8 api
     */
    private static void better() {
        System.out.println("better");
        LocalDateTime localDateTime = LocalDateTime.of(2019, Month.DECEMBER, 31, 11, 12, 13);
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime);
    }
}

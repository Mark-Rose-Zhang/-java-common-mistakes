package org.zhang.mistakes.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/21 16:22
 *
 *
 * 时区问题
 */
public class ZoneQuestion {
    public static void main(String[] args) throws ParseException {
        wrong1();
        wrong2();
    }


    /**
     * 同一个字面量在不同的时区转换为的Date不同
     */
    private static void wrong1() throws ParseException {
        System.out.println("wrong1");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = dateFormat.parse(stringDate);
        System.out.println(d1 + ":" + d1.getTime());
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date d2 = dateFormat.parse(stringDate);
        System.out.println(d2 + ":" + d2.getTime());
    }


    /**
     * 格式化错乱问题(同一个Date在不同地区格式化结果不同)
     * @throws ParseException
     */
    private static void wrong2() throws ParseException {
        System.out.println("wrong2");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = inputFormat.parse(stringDate);
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
    }
}

package org.zhang.mistakes.numbercalculation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/10 16:58
 * 舍入问题
 */
public class RoundProblem {
    private static void wrong1() {

        double num1 = 3.35;
        float num2 = 3.35f;
        System.out.printf("%.1f%n", num1);//四舍五入
        System.out.printf("%.1f%n", num2);
    }

    private static void wrong2() {
        double num1 = 3.35;
        float num2 = 3.35f;
        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num1));
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num2));
    }

    private static void right() {
        BigDecimal num1 = new BigDecimal("3.35");
        BigDecimal num2 = num1.setScale(1, RoundingMode.DOWN);
        System.out.println(num2);
        BigDecimal num3 = num1.setScale(1, RoundingMode.HALF_UP);
        System.out.println(num3);
    }
}

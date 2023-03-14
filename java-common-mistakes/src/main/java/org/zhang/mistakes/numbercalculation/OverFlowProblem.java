package org.zhang.mistakes.numbercalculation;

import java.math.BigInteger;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/10 17:04
 *
 * 注意数值溢出问题
 */
public class OverFlowProblem {
    private static void wrong() {
        long l = Long.MAX_VALUE;
        System.out.println(l + 1);
        System.out.println(l + 1 == Long.MIN_VALUE);
    }

    private static void right2() {
        try {
            long l = Long.MAX_VALUE;
            System.out.println(Math.addExact(l, 1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void right1() {

        BigInteger i = new BigInteger(String.valueOf(Long.MAX_VALUE));
        System.out.println(i.add(BigInteger.ONE).toString());

        try {
            long l = i.add(BigInteger.ONE).longValueExact();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

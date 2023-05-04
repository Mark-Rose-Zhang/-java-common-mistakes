package org.zhang.mistakes.numbercalculation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/10 17:00
 *
 * BigDecimal ---> 比较的是 scale ,precision 内的数据是否相同
 *  1. 哈希表存储时使用 TreeMap 而非 HashMap
 *  2. 数据存储之前可以调用 stripTrailingZeros() 去掉多余的尾0
 */
public class EqualsProblem {
    public static void main(String[] args) {
        System.out.println(new BigDecimal("0.0000").compareTo(new BigDecimal("0.000")));
    }

    private static void wrong() {
        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1")));
    }

    private static void right() {
        System.out.println(new BigDecimal("1.0").compareTo(new BigDecimal("1")) == 0);
    }

    private static void set() {
        Set<BigDecimal> hashSet1 = new HashSet<>();
        hashSet1.add(new BigDecimal("1.0"));
        System.out.println(hashSet1.contains(new BigDecimal("1")));//返回false

        Set<BigDecimal> hashSet2 = new HashSet<>();
        hashSet2.add(new BigDecimal("1.0").stripTrailingZeros());
        System.out.println(hashSet2.contains(new BigDecimal("1.000").stripTrailingZeros()));//返回true

        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(new BigDecimal("1.0"));
        System.out.println(treeSet.contains(new BigDecimal("1")));//返回true
    }

}

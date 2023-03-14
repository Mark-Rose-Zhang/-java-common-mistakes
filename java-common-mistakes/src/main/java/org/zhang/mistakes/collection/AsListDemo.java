package org.zhang.mistakes.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/13 17:06
 *
 *
 * 演示 Arrays.asList()
 */
@Slf4j
public class AsListDemo {
    public static void main(String[] args) {
        wrong2();
    }

    /**
     * asList() 不能正确解析基本数据类型的数组
     */
    private void wrong1() {
        int[] arr = {1,2,3};
        List<int[]> list = Arrays.asList(arr);
        log.info("list:{} size:{} class:{}", list, list.size(), list.get(0).getClass());
    }

    /**
     * 解决方案:
     *      1. 利用 Stream 流进行装箱
     *      2. 声明 包装类型的数组
     */
    private static void right1() {
        int[] arr1 = {1, 2, 3};
        List list1 = Arrays.stream(arr1).boxed().collect(Collectors.toList());
        log.info("list:{} size:{} class:{}", list1, list1.size(), list1.get(0).getClass());

        Integer[] arr2 = {1, 2, 3};
        List list2 = Arrays.asList(arr2);
        log.info("list:{} size:{} class:{}", list2, list2.size(), list2.get(0).getClass());
    }

    /**
     * asList() 获取的 List 不允许进行修改操作
     */
    private static void wrong2() {
        String[] arr = {"1", "2", "3"};
        List<String> list = Arrays.asList(arr);
        arr[1] = "4";
        try {
            list.add("5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("arr:{} list:{}", Arrays.toString(arr), list);
    }

    /**
     * 将获取的 List 作为参数传递给 ArrayList
     */
    private static void right2() {
        String[] arr = {"1", "2", "3"};
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        arr[1] = "4";
        try {
            list.add("5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("arr:{} list:{}", Arrays.toString(arr), list);
    }
}

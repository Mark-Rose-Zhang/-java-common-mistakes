package org.zhang.mistakes.advancefuture;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/23 17:17
 *
 * 反射问题
 */
@Slf4j
public class ReflectionIssue {
    public static void main(String[] args) throws Exception {

        ReflectionIssue application = new ReflectionIssue();
        application.wrong();
        application.right();

        System.out.println(Integer.TYPE == Integer.class);
    }

    private void age(int age) {
        log.info("int age = {}", age);
    }

    private void age(Integer age) {
        log.info("Integer age = {}", age);
    }

    public void wrong() throws Exception {
        // 不能按照类型处理
        getClass().getDeclaredMethod("age", Integer.TYPE).invoke(this, Integer.valueOf("36"));
    }

    public void right() throws Exception {
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, Integer.valueOf("36"));
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, 36);
    }
}

package org.zhang.mistakes.advancefuture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;

@Slf4j
public class AnnotationInheritance {

    public static void main(String[] args) throws NoSuchMethodException {
        wrong();
        right();
    }

    private static String getAnnotationValue(MyAnnotation annotation) {
        if (annotation == null) return "";
        return annotation.value();
    }

    public static void wrong() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("foo").getAnnotation(MyAnnotation.class)));


        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(child.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(child.getClass().getMethod("foo").getAnnotation(MyAnnotation.class)));

    }

    public static void right() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("foo").getAnnotation(MyAnnotation.class)));

        // 利用Spring的工具类可以获取 AnnotatedElementUtils 可以查找父类的注解
        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass(), MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass().getMethod("foo"), MyAnnotation.class)));

    }

    @MyAnnotation(value = "Class")
    @Slf4j
    static class Parent {

        @MyAnnotation(value = "Method")
        public void foo() {
        }
    }

    @Slf4j
    static class Child extends Parent {
        @Override
        public void foo() {
        }
    }
}

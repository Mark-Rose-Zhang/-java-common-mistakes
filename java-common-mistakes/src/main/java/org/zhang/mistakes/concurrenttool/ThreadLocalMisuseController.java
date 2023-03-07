package org.zhang.mistakes.concurrenttool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/7 14:06
 */
@RestController
@RequestMapping("/threadlocal")
public class ThreadLocalMisuseController {
    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);
    @GetMapping("wrong")
    public Map<String,String> wrong(@RequestParam("userId") Integer userId) {
        String before  = Thread.currentThread().getName() + ":" + currentUser.get();
        // 设置了当前线程 (tomcat 线程) 的ThreadLocal对象参数
        currentUser.set(userId);
        String after  = Thread.currentThread().getName() + ":" + currentUser.get();
        Map<String,String> result = new HashMap<>();
        result.put("before", before);
        result.put("after", after);
        // 结束之前并未释放
        return result;
    }


    @GetMapping("right")
    public Map<String,String> right(@RequestParam("userId") Integer userId) {
        String before  = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        try {
            String after = Thread.currentThread().getName() + ":" + currentUser.get();
            Map<String,String> result = new HashMap<>();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            // 删除对应的值
            currentUser.remove();
        }
    }
}

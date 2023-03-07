package org.zhang.mistakes.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/7 14:46
 * 注意锁的对象是谁
 */
@RestController
@RequestMapping("lockscope")
@Slf4j
public class LockScopeController {

    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
        return Data.getCounter();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().right());
        return Data.getCounter();
    }

    @GetMapping("wrong2")
    public String wrong2() {
        Interesting interesting = new Interesting();
        new Thread(interesting::add).start();
        new Thread(interesting::compare).start();
        return "OK";
    }

    @GetMapping("right2")
    public String right2() {
        Interesting interesting = new Interesting();
        new Thread(interesting::add).start();
        new Thread(interesting::compareRight).start();
        return "OK";
    }
}
package org.zhang.mistakes.concurrenttool;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/7 14:22
 */
public class StreamTest {

    @Test
    void stream() {
        ConcurrentHashMap<String, Integer> map = LongStream.rangeClosed(1, 10).boxed().collect(Collectors.toConcurrentMap(
                i -> i + ":" + UUID.randomUUID().toString(), i -> 1,
                Integer::sum, ConcurrentHashMap::new));
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
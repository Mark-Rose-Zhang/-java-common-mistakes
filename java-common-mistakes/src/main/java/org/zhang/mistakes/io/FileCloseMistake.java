package org.zhang.mistakes.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * @author nancheng
 *
 * 注意句柄的释放问题 对于 java.nio.file.Files 返回的 Sreams 利用 try-with-resource 实现文件句柄的关闭
 */
@Slf4j
public class FileCloseMistake {

    public static void main(String[] args) throws IOException {
        init();
        wrong();
    }

    private static void readLargeFileWrong() throws IOException {
        log.info("lines {}", Files.readAllLines(Paths.get("large.txt")).size());
    }

    private static void readLargeFileRight() throws IOException {
        AtomicLong atomicLong = new AtomicLong();
        Files.lines(Paths.get("large.txt")).forEach(line -> atomicLong.incrementAndGet());
        log.info("lines {}", atomicLong.get());
    }

    private static void linesTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("read 200000 lines");
        log.info("lines {}", Files.lines(Paths.get("large.txt")).limit(200000).collect(Collectors.toList()).size());
        stopWatch.stop();
        stopWatch.start("read 2000000 lines");
        log.info("lines {}", Files.lines(Paths.get("large.txt")).limit(2000000).collect(Collectors.toList()).size());
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    private static void init() throws IOException {

        String payload = IntStream.rangeClosed(1, 1000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString();

        Files.write(Paths.get("demo.txt"),
                IntStream.rangeClosed(1, 10).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList())
                , UTF_8, CREATE, TRUNCATE_EXISTING);
    }

    private static void wrong() {
        LongAdder longAdder = new LongAdder();
        IntStream.rangeClosed(1, 1000000).forEach(i -> {
            try {
                Files.lines(Paths.get("demo.txt")).forEach(line -> longAdder.increment());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("total : {}", longAdder.longValue());
    }

    private static void right() {
        //https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html
        LongAdder longAdder = new LongAdder();
        IntStream.rangeClosed(1, 1000000).forEach(i -> {
            try (Stream<String> lines = Files.lines(Paths.get("demo.txt"))) {
                lines.forEach(line -> longAdder.increment());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("total : {}", longAdder.longValue());
    }
}

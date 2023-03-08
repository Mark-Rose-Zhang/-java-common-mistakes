package org.zhang.mistakes.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/7 14:06
 */
@Service
@Slf4j
public class UserService {

    /**
     * 异常被内部捕获，并未抛出
     * @param name
     */
    @Transactional
    public void createUserWrong1(String name) {
        try {
            log.info("do someThing");
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
        }
    }

    /**
     * 抛出的异常不是非受检异常
     * @param name
     * @throws IOException
     */
    @Transactional
    public void createUserWrong2(String name) throws IOException {
        log.info("do Something");
        otherTask();
    }

    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }

    public int getUserCount(String name) {
        return 0;
    }


    @Transactional
    public void createUserRight1(String name) {
        try {
            log.info("do someThing");
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.info("result {} ",1);//为什么这里是1你能想明白吗？ 因为整个方法执行完成之后,事务才进行回滚的
    }

    //DefaultTransactionAttribute
    @Transactional(rollbackFor = Exception.class)
    public void createUserRight2(String name) throws IOException {
        log.info("do someThing");
        otherTask();
    }

}
package org.zhang.mistakes.execption;

/**
 * @author nancheng
 * 实现 AutoCloseable 接口
 */
public class TestResource implements AutoCloseable {

    public void read() throws Exception {
        throw new Exception("read error");
    }

    @Override
    public void close() throws Exception {
        throw new Exception("close error");
    }

}
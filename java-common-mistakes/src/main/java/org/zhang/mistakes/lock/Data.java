package org.zhang.mistakes.lock;

import lombok.Getter;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/7 14:06
 */
class Data {
    @Getter
    private static int counter = 0;
    private static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    /**
     * 此时为 synchronized(this)
     */
    public synchronized void wrong() {
        counter++;
    }

    public void right() {
        synchronized (locker) {
            counter++;
        }
    }
}
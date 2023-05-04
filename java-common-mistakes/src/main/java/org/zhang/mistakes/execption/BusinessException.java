package org.zhang.mistakes.execption;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/14 17:07
 */
public class BusinessException extends RuntimeException{

    private int code;
    public BusinessException(String message,int code) {
        super(message);
        this.code = code;
    }


    public int getCode() {
        return this.code;
    }
}

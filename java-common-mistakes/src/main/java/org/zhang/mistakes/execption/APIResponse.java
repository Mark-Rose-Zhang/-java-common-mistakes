package org.zhang.mistakes.execption;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author nancheng
 *
 */
@Data
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
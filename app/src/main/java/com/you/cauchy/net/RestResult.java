package com.you.cauchy.net;

import lombok.Data;

@Data
public class RestResult<T> {

    private Integer code;
    private String message;
    private T data;

    public static int SUCCESS = 0;
}

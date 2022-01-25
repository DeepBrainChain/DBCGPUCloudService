package com.dbc.entity;

import lombok.Data;

/**
 *  数据返回的类
 * @param <T>
 */
@Data
public class ResultVO<T> {

            private Integer code;
            private String message;
            private T data;


            public ResultVO(Integer code, String message) {
                this.code = code;
                this.message = message;
            }

            public ResultVO(Integer code, String message, T data) {
                this.code = code;
                this.message = message;
                this.data = data;
            }

}

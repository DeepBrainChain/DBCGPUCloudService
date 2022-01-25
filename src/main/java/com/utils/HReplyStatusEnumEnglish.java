package com.utils;

/**
 * @author feng 2019/8/2
 */
public enum HReplyStatusEnumEnglish {
    
	
    SUCCESS(1, "0", ""),
    ERROR(-1, "0", "Unknown Error"),

    DBCTRADE_GET_DBC_COUNT_SUCCESS(-1, "10001", "验证人可以审核的机器"),
    DBCTRADE_GET_DBC_COUNT_SUCCESS23(-1, "10002", "钱包没绑定邮箱"),
    DBCTRADE_GET_DBC_COUNT_SUCCESS245(-1, "10099", "unknown error"),


    //DBCTRADE_GET_DBC_COUNT_SUCCESS245(-1, "10100", "unknown error"),
   
    ;

    private String code;
    private String msg;
    private Integer status;

    HReplyStatusEnumEnglish(Integer status, String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.status = status;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

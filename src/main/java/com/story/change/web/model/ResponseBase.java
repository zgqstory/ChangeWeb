package com.story.change.web.model;

import java.io.Serializable;

/**
 * Created by story on 2017/2/23 0023.
 * 返回数据基类
 */
public class ResponseBase implements Serializable {
    private static final long serialVersionUID = -7138795326699232077L;
    private boolean success;
    private String code;
    private String message;
    private String body;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

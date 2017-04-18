package com.story.change.web.model;

import java.io.Serializable;

/**
 * Created by story on 2017/2/23 0023.
 * 返回数据基类
 */
public class ResponseBase implements Serializable {
    private static final long serialVersionUID = -7138795326699232077L;
    private String rspType;
    private String rspCode;
    private String rspMsg;
    private Object rspData;

    public String getRspType() {
        return rspType;
    }

    public void setRspType(String rspType) {
        this.rspType = rspType;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public Object getRspData() {
        return rspData;
    }

    public void setRspData(Object rspData) {
        this.rspData = rspData;
    }
}

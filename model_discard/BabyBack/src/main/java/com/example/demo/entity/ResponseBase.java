package com.example.demo.entity;



public class ResponseBase {
    public Integer rtnCode;
    private String msg;
    private Object data;

    /****
     * 成功返回
     * @param msg
     * @return
     */
    public ResponseBase succes(String msg) {
        return new ResponseBase(200, msg, null);
    }

    public ResponseBase error(String e) {
        return new ResponseBase(500, e, null);
    }

    public ResponseBase() {
    }

    public ResponseBase(Integer rtnCode, String msg, Object data) {
        this.rtnCode = rtnCode;
        this.msg = msg;
        this.data = data;
    }

    public Integer getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(Integer rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}


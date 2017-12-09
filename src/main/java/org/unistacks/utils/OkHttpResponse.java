package com.unistack.tamboo.utils;

import com.google.common.base.MoreObjects;

/**
 * @author mazean
 */
public class OkHttpResponse {

    private int code;

    private String body;

    public OkHttpResponse(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        code = code;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Code", code)
                .add("body", body)
                .toString();
    }
}

package com.novang.anisched.model.anissia;

public class AnissiaResponse<T> {

    private String code;

    private T data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

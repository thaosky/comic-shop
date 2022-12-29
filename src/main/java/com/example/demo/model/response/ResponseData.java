package com.example.demo.model.response;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {
    private int status;
    private String error;
    private String path;
    private T data;

    public ResponseData() {
    }

    public ResponseData(int status, String error, String path, T data) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.data = data;
    }

    public ResponseData(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }
}

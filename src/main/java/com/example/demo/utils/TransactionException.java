package com.example.demo.utils;

public class TransactionException extends Throwable {
    private int errorCode;

    private String title;

    private String msg;

    public TransactionException(int errorCode, String title, String msg) {
        this.errorCode = errorCode;
        this.title = title;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TransactionException{" +
                "errorCode=" + errorCode +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

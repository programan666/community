package com.programan.cm.common.utils;

public final class JSONResult<T> {

    public static final String SUCCESS_CODE = "0";
    public static final String ERROR_CODE = "-1";

    public String status = SUCCESS_CODE;
    /**
     * 提示信息
     */
    private String errorMsg;

    /**
     * 允许是Map, List, Java Bean 等等
     * 有的时候也是类似Map<String, List<SomeBean>> ......
     */
    private T context;
    /**
     * @param status
     * @param msg
     * @param content
     * @return
     */
    public static JSONResult success(String status, String msg, Object content) {
        return new JSONResult().setStatus(status).setErrorMsg(msg).setContext(content);
    }

    public static <T> JSONResult<T> success(T content) {
        return new JSONResult<T>().setStatus(SUCCESS_CODE).setContext(content);
    }

    public static JSONResult failed(String msg) {
        return new JSONResult<String>().setStatus(ERROR_CODE).setErrorMsg(msg);
    }

    /**
     * @param status
     * @param msg
     * @param content
     * @return
     */
    public static JSONResult failed(String status, String msg, Object content) {
        return new JSONResult().setStatus(status).setErrorMsg(msg).setContext(content);
    }

    //getter and setter
    public String getStatus() {
        return status;
    }

    public JSONResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public JSONResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public T getContext() {
        return context;
    }

    private JSONResult setContext(T context) {
        this.context = context;
        return this;
    }

}

package com.hx.webim.model.vo;

import com.hx.webim.common.ResultEnum;

public class ResultVo<T> {


    private int code;
    private String msg;
    private T data;

    public ResultVo(){

    }
    public ResultVo(ResultEnum resultEnum) {
        code=resultEnum.getCode();
        msg=resultEnum.getMessage();
    }


    public void setCodeAndMsg(ResultEnum resultEnum){
        code=resultEnum.getCode();
        msg=resultEnum.getMessage();
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

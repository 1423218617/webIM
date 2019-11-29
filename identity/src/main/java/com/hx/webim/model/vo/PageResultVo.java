package com.hx.webim.model.vo;

public class PageResultVo<T> {
    private Integer code;
    private Integer pages;
    private T data;
    private Integer memberIdx;

    public Integer getMemberIdx() {
        return memberIdx;
    }

    public void setMemberIdx(Integer memberIdx) {
        this.memberIdx = memberIdx;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageResultVo{" +
                "code=" + code +
                ", pages=" + pages +
                ", data=" + data +
                ", memberIdx='" + memberIdx + '\'' +
                '}';
    }
}

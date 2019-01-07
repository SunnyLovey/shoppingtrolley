package com.bawei.demo.shoppingtrolley.eventbusbean;

public class MessageBean {
    private int flag;
    private Object object;

    public MessageBean(int flag, Object object) {
        this.flag = flag;
        this.object = object;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

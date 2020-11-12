package com.edu.base;

public enum UsedCode {

    USED(1),
    UNUSED(0);

    private int vipCode;

    UsedCode(int vipCode) {
        this.vipCode = vipCode;
    }

    public int getVipCode() {
        return this.vipCode;
    }

}

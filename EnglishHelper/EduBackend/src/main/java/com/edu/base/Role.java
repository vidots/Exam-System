package com.edu.base;

public enum Role {
    USER("user"), VIP("vip");
    private String role;
    Role(String role) {
        this.role = role;
    }
    public String getRole() {
        return this.role;
    }
}

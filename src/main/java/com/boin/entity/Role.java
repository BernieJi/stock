package com.boin.entity;

public enum Role {
    USER,
    ADMIN;

    public String getString(){
        return this.name();
    }
}

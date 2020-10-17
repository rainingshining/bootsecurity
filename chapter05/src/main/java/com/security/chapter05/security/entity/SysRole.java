package com.security.chapter05.security.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
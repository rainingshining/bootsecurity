package com.security.chapt02.security.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;
}

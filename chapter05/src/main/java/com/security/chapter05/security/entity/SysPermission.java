package com.security.chapter05.security.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SysPermission implements Serializable {

    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;

    private List permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
//        return permissions;
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }
}

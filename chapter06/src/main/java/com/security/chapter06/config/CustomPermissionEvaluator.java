package com.security.chapter06.config;

import com.security.chapter06.security.entity.SysPermission;
import com.security.chapter06.security.service.CustomUserDetailsService;
import com.security.chapter06.security.service.SysPermissionService;
import com.security.chapter06.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        /*存在问题，当不注入CustomUserDetailsService时，无法通过首次登陆的信息自动从Authentication中获取相应的UserDetalis，
        从authentication里获取的只是String类型的Principal，异常信息：java.lang.String cannot be cast to org.springframework.security.core.userdetails.User
        * 临时使用再次调用接口的方式再次获取UserDetails*/
        String name = authentication.getName();
        User user = (User)userDetailsService.loadUserByUsername(name);
        // 获得loadUserByUsername()方法的结果
//        User user = (User)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        String targetUrl = (String) o;
        String targetPermission = (String) o1;

        // 遍历用户所有角色
        for(GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Integer roleId = roleService.selectByName(roleName).getId();
            // 得到角色所有的权限
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);

            // 遍历permissionList
            for(SysPermission sysPermission : permissionList) {
                // 获取权限集
                List permissions = sysPermission.getPermissions();
                // 如果访问的Url和权限用户符合的话，返回true
                if(targetUrl.equals(sysPermission.getUrl())
                        && permissions.contains(targetPermission)) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}

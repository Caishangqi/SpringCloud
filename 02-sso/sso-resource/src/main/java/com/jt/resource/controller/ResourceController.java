package com.jt.resource.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    /**
     * @PreAuthorize 注解描述的方法是一个权限切入点方法，
     * 访问此方法时需要授权才可以访问
     * @return
     */

    @PreAuthorize("hasAuthority('sys:res:select')")
    @GetMapping
    public String doSelect(){
        return "select resource";
    }
    @PreAuthorize("hasAuthority('sys:res:create')")
    @PostMapping
    public String doCreate(){
        return "create resource";
    }
    @PreAuthorize("hasAuthority('sys:res:update')")
    @PutMapping
    public String doUpdate(){
        return "update resource";
    }
    @DeleteMapping
    public String doDelete(){
        return "delete resource";
    }
}

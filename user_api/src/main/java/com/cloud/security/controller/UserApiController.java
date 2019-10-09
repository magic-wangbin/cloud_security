package com.cloud.security.controller;

import com.cloud.security.service.UserService;
import com.cloud.security.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserApiController {
    @Autowired
    private UserService userService;

    /**
     * 注册.
     */
    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo userInfo) {
        return userService.create(userInfo);
    }

    /**
     * 改
     */
    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userInfo;
    }

    // ---------------------------------------------查询部分begin-------------------------------------------------
    @GetMapping("/{id}")
    public UserInfo get(@PathVariable Long id, HttpServletRequest request) {

        UserInfo user = (UserInfo) request.getAttribute("user");
        if (user == null || !id.equals(user.getUserId())) {
            throw new RuntimeException("身份认证信息异常，获取用户信息失败");
        }
        return userService.get(id);

    }

    /**
     * 查询1
     */
    @GetMapping("/queryByName")
    public List<UserInfo> query(String username) {
        return userService.findAllByName(username);
    }

    // ---------------------------------------------查询部分end-------------------------------------------------

    /**
     * 删
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        //TODO
    }
}

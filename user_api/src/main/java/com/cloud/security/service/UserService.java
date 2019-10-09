package com.cloud.security.service;

import com.cloud.security.vo.UserInfo;

import java.util.List;

public interface UserService {

    UserInfo create(UserInfo user);

    UserInfo update(UserInfo user);

    void delete(Long id);

    UserInfo get(Long id);

    UserInfo login(UserInfo user);

    List<UserInfo> findAllByName(String username);

    UserInfo findByUserName(String userName);
}

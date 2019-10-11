package com.cloud.security.service.impl;

import com.cloud.security.dao.UserRepository;
import com.cloud.security.entity.User;
import com.cloud.security.service.UserService;
import com.cloud.security.vo.UserInfo;
import com.google.common.collect.Lists;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(), 32768, 8, 1));
        userRepository.save(user);
        userInfo.setUserId(user.getUserId());
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo userInfo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserInfo get(Long id) {
        return userRepository.findById(id).get().buildInfo();
    }

    @Override
    public UserInfo login(UserInfo user) {
        return null;
    }

    @Override
    public List<UserInfo> findAllByName(String username) {
        List<User> users = userRepository.findAllByUserName(username);
        List<UserInfo> userInfos = Lists.newArrayList();
        for (User user : users) {
            userInfos.add(user.buildInfo());
        }
        return userInfos;
    }

    @Override
    public UserInfo findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return user.buildInfo();
    }
}

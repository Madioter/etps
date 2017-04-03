package com.madiot.enterprise.service;

import com.madiot.enterprise.model.ResponseVo;
import com.madiot.enterprise.model.User;

import java.util.List;

/**
 * Created by Administrator on 2016/1/3 0003.
 */
public interface IUserService {

    public User doLogin(String username, String password);

    public List<User> queryUserPageByCondition(String username, int rows, int page);

    public int countUserByCondition(String username);

    public int savePassword(Integer id, String password);

    public int saveUser(User user);

    public int deleteUser(Integer id);

    public void deleteUsers(List<Integer> idList);
}


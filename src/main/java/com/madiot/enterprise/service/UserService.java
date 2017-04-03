package com.madiot.enterprise.service;

import com.madiot.enterprise.dao.UserDao;
import com.madiot.enterprise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/1/3 0003.
 */
@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User doLogin(String username, String password) {
        return userDao.doLogin(username, password);
    }

    public List<User> queryUserPageByCondition(String username, int rows, int page) {
        return userDao.selectUserByName(username, (page - 1) * rows, rows);
    }

    @Override
    public int countUserByCondition(String username) {
        return userDao.countUserByName(username);
    }

    @Override
    public int savePassword(Integer id, String password) {
        return userDao.updatePassword(id, password);
    }

    @Override
    public int saveUser(User user) {
        checkUserExist(user);
        if (user.getId() == null) {
            return userDao.insertUser(user);
        } else {
            return userDao.updateUser(user);
        }
    }

    private boolean checkUserExist(User user) {
        int count = userDao.checkUserExist(user.getUsername(), user.getId());
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    @Override
    public void deleteUsers(List<Integer> idList) {
        for (Integer id : idList) {
            if (id != null) {
                deleteUser(id);
            }
        }
    }
}

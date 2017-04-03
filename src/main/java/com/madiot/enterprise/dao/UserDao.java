package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/1/3 0003.
 */
public interface UserDao {

    public User doLogin(@Param("username") String username,
                        @Param("password") String password);

    public List<User> selectUserByName(@Param("username") String username,
                                       @Param("startNum") int startNum,
                                       @Param("pageSize") int pageSize);

    public int countUserByName(@Param("username") String username);

    public int updatePassword(@Param("id") Integer id,
                              @Param("password") String password);

    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUser(Integer id);

    public int checkUserExist(@Param("username") String username,
                              @Param("id") Integer id);
}

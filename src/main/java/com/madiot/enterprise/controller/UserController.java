package com.madiot.enterprise.controller;

import com.madiot.enterprise.common.AuthUtil;
import com.madiot.enterprise.common.CommonConstant;
import com.madiot.enterprise.common.util.StringUtil;
import com.madiot.enterprise.model.ResponseList;
import com.madiot.enterprise.model.ResponseVo;
import com.madiot.enterprise.model.User;
import com.madiot.enterprise.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/3 0003.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/userList")
    public String userList(HttpSession session) {
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            return "common/login";
        }
        return "user/userList";
    }

    @RequestMapping("/userListJson")
    @ResponseBody
    public ResponseList userListJson(HttpSession session, String username, int rows, int page) {
        ResponseList<User> responseList = new ResponseList<User>();
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            responseList.setRows(new ArrayList<User>());
            responseList.setTotal(0);
            return responseList;
        }
        List<User> userList = userService.queryUserPageByCondition(username, rows, page);
        int total = userService.countUserByCondition(username);
        responseList.setRows(userList);
        responseList.setTotal(total);
        return responseList;
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(HttpSession session, ModelMap mv, Integer id) {
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            return "common/login";
        }
        mv.put("id", id);
        return "user/resetPassword";
    }

    @RequestMapping("/editUser")
    public ModelAndView editUser(HttpSession session, Integer id) {
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            return new ModelAndView("common/login");
        }
        ModelAndView mv = new ModelAndView("user/editUser");
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("/changePwd")
    @ResponseBody
    public ResponseVo changePwd(HttpSession session, Integer id, String password, String repassword) {
        ResponseVo responseVo = new ResponseVo();
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            responseVo.setMessage("权限不足，不能修改");
            return responseVo;
        }
        if (!password.equals(repassword)) {
            responseVo.setMessage("两次输入的密码不一致");
            return responseVo;
        }
        userService.savePassword(id, password);
        responseVo.setMessage("修改成功");
        responseVo.setSuccess(true);
        return responseVo;
    }

    @RequestMapping("saveUser")
    @ResponseBody
    public ResponseVo saveUser(HttpSession session, User user) {
        ResponseVo responseVo = new ResponseVo();
        User loginUser = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(loginUser)) {
            responseVo.setMessage("权限不足，不能操作");
            return responseVo;
        }
        userService.saveUser(user);
        responseVo.setSuccess(true);
        responseVo.setMessage("保存成功");
        return responseVo;
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public ResponseVo deleteUser(HttpSession session, Integer id, String ids){
        ResponseVo responseVo = new ResponseVo();
        List<Integer> idList = new ArrayList<Integer>();
        if (id != null) {
            idList.add(id);
        }
        if (ids != null) {
            idList = StringUtil.idsToList(ids);
        }
        if (CollectionUtils.isEmpty(idList)) {
            responseVo.setMessage("请选择删除的用户");
            return responseVo;
        }
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            responseVo.setMessage("权限不足，不能操作");
            return responseVo;
        }
        userService.deleteUsers(idList);
        responseVo.setSuccess(true);
        responseVo.setMessage("删除成功");
        return responseVo;
    }


}

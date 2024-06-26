package com.gxxstudy.service;

import com.gxxstudy.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author guxiangxiang
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-06-04 21:54:47
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 新用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword,HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
}

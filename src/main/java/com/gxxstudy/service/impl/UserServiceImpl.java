package com.gxxstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxxstudy.model.User;
import com.gxxstudy.service.UserService;
import com.gxxstudy.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.gxxstudy.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author guxiangxiang
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-06-04 21:54:47
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 密码加密的盐值
     */
    private static final String SALT = "gxx";

    @Override
    public long userRegister(String userAccount,String userPassword,String checkPassword)
    {
        //1. 校验逻辑
        // 校验null
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)) {
            //todo修改为自定义异常
            return -1;
        }
        //校验账户长度不小于 4
        if (userAccount.length() < 4) {
            return -1;
        }
        // 校验密码长度，不小于 8
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // 账户不能包含特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(validPattern)) {
            return -1;
        }
        // 校验 密码和校验密码不一致
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();  //需要查询数据库，放在账户不能包含特殊字符代码后面，省去一次查询数据库的过程，节省性能
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0){
            return -1;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if(!saveResult){ //保存失败
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1. 校验逻辑
        if (StringUtils.isAnyBlank(userAccount,userPassword)) {
            return null;
        }
        //校验账户长度不小于 4
        if (userAccount.length() < 4) {
            return null;
        }
        // 校验密码长度，不小于 8
        if (userPassword.length() < 8) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(validPattern)) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("userAccount",userAccount).eq("userPassword",encryptPassword));
        // 用户不存在
        if(user == null){
            log.info("user login failed,userAccount can not match userPassword");
            return null;
        }
        // 3. 用户脱敏 -允许返回给前端的值
        User safetyUser = getSafetyUser(user);
        //4. 记录用户的登陆态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if(originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

}





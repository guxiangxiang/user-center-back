package com.gxxstudy.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author gxx
 * @version 1.0
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}

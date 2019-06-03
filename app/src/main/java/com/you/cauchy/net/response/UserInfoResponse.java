package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfoResponse {

    private Integer id;
    private String account;
    private String password;
    private String chineseName;
    private Integer gender;
    private String phone;
    private String avatarUrl;
    private Integer role;
    private String academy;
    private String degree;
    private Date createTime;
    private Date updateTime;
}

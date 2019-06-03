package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class DiscussResponse {

    private Integer id;
    private Integer courseCode;
    private Integer userId;
    private Integer employeeId;
    private Integer replyTargetId;
    private Integer replyTargetUserId;
    private Integer replyTargetEmployeeId;
    private String content;
    private Date createTime;

    private String userAvatar;
    private String userName;
    private String employName;
    private String replyTargetUserName;
    private String replyTargetEmployeeName;
}

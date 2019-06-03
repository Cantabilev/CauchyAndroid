package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class UserExamResponse {

    private Integer id;
    private Integer userId;
    private Integer examinationId;
    private Date answerTime;
    private Integer score;
    private Date createTime;

    private String type;
    private String title;
    private String subtitle;
    private Integer fullMark;
}

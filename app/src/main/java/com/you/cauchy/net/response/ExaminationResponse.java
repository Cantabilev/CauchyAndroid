package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class ExaminationResponse {

    // examination id
    private Integer id;
    private Integer courseInfoTemplateId;
    private String type;
    private String title;
    private String subtitle;

    private Integer courseId;
    private String courseName;

    private Integer uploader;
    private String uploaderName;
    private Date createTime;
    private Integer submitAmount;

    private String remark;
    private Integer examinationTime;
    private Integer fullMark;

}

package com.you.cauchy.net.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CourseCodeRecommendResponse {
    private Integer id;
    private Integer courseCode;
    private Integer status;
    private Integer seq;

    private Integer courseId;
    private String courseName;
    private BigDecimal courseAmount;
    private String courseInfo;
    private String picUrl;
    private String recommendUrl;
    private Date startTime;
    private Date endTime;
}

package com.you.cauchy.net.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class HotCourseResponse {
    private Integer id;
    private Integer courseId;
    private Integer courseCode;

    private String courseName;
    private BigDecimal courseAmount;
    private String picUrl;
    private String recommendUrl;
    private Date startTime;
    private Date endTime;

    /**
     * 课程情况,有多少人
     */
    private Integer situation;

    /**
     * 课程容量
     */
    private Integer capacity;

    /**
     * 课程节数
     */
    private Integer quantity;
}

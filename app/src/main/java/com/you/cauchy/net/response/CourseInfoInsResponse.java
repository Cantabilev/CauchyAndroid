package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class CourseInfoInsResponse {
    private Integer id;
    private Integer courseId;
    private Integer courseInfoTemplateId;
    private Integer courseCode;
    private String courseTitle;
    private String courseSubtitle;
    private String courseInfo;
    private String address;
    private Date startTime;
    private Date endTime;
}

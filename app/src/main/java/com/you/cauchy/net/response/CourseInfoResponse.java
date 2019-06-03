package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class CourseInfoResponse {
    private Integer id;
    private Integer courseCode;
    private String courseName;
    private Integer codeInstanceId;
    private Date startTime;
    private Date endTime;
}

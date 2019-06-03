package com.you.cauchy.net.response;

import java.util.Date;

import lombok.Data;

@Data
public class UserExpectResponse {

    private Integer id;
    private Integer userId;
    private Integer courseId;
    private Integer expectNameId;
    private String expectValue;

    private String courseName;
    private String expectName;
}

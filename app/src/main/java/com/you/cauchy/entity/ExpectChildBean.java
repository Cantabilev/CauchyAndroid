package com.you.cauchy.entity;

import lombok.Data;

@Data
public class ExpectChildBean {

    private Integer id;
    private Integer courseId;
    private String courseName;
    private String expectName;
    private String expectValue;
}

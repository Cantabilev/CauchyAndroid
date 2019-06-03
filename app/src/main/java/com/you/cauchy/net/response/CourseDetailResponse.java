package com.you.cauchy.net.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CourseDetailResponse {

    // course_code_id
    private Integer id;
    private Integer courseCode;
    private String employeeName;
    private Integer situation;
    private Integer capacity;
    private Integer quantity;

    private String courseName;
    private BigDecimal courseAmount;
    private String courseInfo;
    private String picUrl;
    private String recommendUrl;
    private Date startTime;
    private Date endTime;

    /**
     * 课程自带卷 折扣后价格
     */
    private BigDecimal postDiscountPrice;

    /**
     * 是否已经购买
     */
    private Boolean buy;

}

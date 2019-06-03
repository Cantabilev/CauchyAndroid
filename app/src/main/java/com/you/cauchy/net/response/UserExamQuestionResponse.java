package com.you.cauchy.net.response;

import lombok.Data;

@Data
public class UserExamQuestionResponse {
    private Integer id;
    private Integer examinationId;
    private Integer userExaminationId;
    private Integer questionId;
    private String answer;
    private String pictureUrl;
    private Integer score;
}

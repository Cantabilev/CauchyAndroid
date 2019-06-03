package com.you.cauchy.net.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserExamDetailResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserExamResponse userExam;
    private List<UserExamQuestion> questions;

    @Data
    public class UserExamQuestion implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer id;
        private Integer examinationId;
        private Integer userExaminationId;
        private Integer questionId;
        private String answer;
        private String pictureUrl;
        private Integer score;


        private String type;
        private Integer mark;
        private String questionContent;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;
    }
}

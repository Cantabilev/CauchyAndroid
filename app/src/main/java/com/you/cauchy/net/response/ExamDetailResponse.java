package com.you.cauchy.net.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ExamDetailResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private ExaminationResponse exam;
    private List<ExaminationQuestion> questions;

    @Data
    public class ExaminationQuestion implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer id;
        private Integer examinationId;

        private String type;
        private Integer mark;
        private String questionContent;
        private String optionTrue;
        private String optionFalse;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;
    }
}

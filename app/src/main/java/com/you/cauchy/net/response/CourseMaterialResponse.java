package com.you.cauchy.net.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CourseMaterialResponse {

    // 图片
    private ArrayList<String> picUrls;

    // 视频
    private ArrayList<String> videoUrls;

    // 测验
    private List<ExaminationResponse> examinations;
}

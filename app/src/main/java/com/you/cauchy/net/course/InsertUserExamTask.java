package com.you.cauchy.net.course;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.you.cauchy.R;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.response.ExamDetailResponse;
import com.you.cauchy.net.response.UserExamQuestionResponse;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertUserExamTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {

    }

    @Override
    public <Params> void execute(Params... params) {
        List<UserExamQuestionResponse> list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) params[1];
        List<ExamDetailResponse.ExaminationQuestion> recyclerData = (List<ExamDetailResponse.ExaminationQuestion>) params[2];
        for (int i = 0; i<recyclerData.size(); i++){
            View view = recyclerView.getChildAt(i);
            ExamDetailResponse.ExaminationQuestion ques = recyclerData.get(i);
            String answer = "";
            switch (ques.getType()){
                case "single": answer = getSingleAnswer(view); break;
                case "multiple": answer = getMultipleAnswer(view); break;
                case "true_or_false": answer = getTfAnswer(view); break;
                case "describe": answer = getDescribeAnswer(view); break;
                default: break;
            }

            UserExamQuestionResponse question = new UserExamQuestionResponse();
            question.setQuestionId(ques.getId());
            question.setAnswer(answer);
            list.add(question);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("examId", params[0]);
        map.put("questions", list);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        OkHttpUtils
                .postString()
                .url(URL + "/api/userExamination")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(jsonStr)
                .build()
                .execute(restCallback);
    }

    private String getSingleAnswer(View view){
        RadioButton A = view.findViewById(R.id.optionA);
        if (A.isChecked())
            return "A";
        RadioButton B = view.findViewById(R.id.optionB);
        if (B.isChecked())
            return "B";
        RadioButton C = view.findViewById(R.id.optionC);
        if (C.isChecked())
            return "C";
        RadioButton D = view.findViewById(R.id.optionD);
        if (D.isChecked())
            return "D";
        return "";
    }

    private String getMultipleAnswer(View view){
        StringBuilder answer = new StringBuilder();
        CheckBox A = view.findViewById(R.id.optionA);
        CheckBox B = view.findViewById(R.id.optionB);
        CheckBox C = view.findViewById(R.id.optionC);
        CheckBox D = view.findViewById(R.id.optionD);
        if (A.isChecked())
            answer.append("A");
        if (B.isChecked())
            answer.append("B");
        if (C.isChecked())
            answer.append("C");
        if (D.isChecked())
            answer.append("D");
        return answer.toString();
    }

    private String getTfAnswer(View view){
        RadioButton A = view.findViewById(R.id.optionTrue);
        if (A.isChecked())
            return "True";
        RadioButton B = view.findViewById(R.id.optionFalse);
        if (B.isChecked())
            return "False";
        return "";
    }

    private String getDescribeAnswer(View view){
        EditText editText = view.findViewById(R.id.etContent);
        return editText.getText().toString();
    }
}

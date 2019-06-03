package com.you.cauchy.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.you.cauchy.R;
import com.you.cauchy.net.response.CourseResponse;
import com.you.cauchy.net.response.ExpectNameResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ExpectDialog extends Dialog implements View.OnClickListener {

    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private Spinner spinnerCourse, spinnerExpect;
    private EditText etValue;

    private Context mContext;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    private Integer courseId, expectNameId;

    private List<CourseResponse> courseList;
    private List<ExpectNameResponse> expectNameList;

    public ExpectDialog(Context context) {
        this(context, R.style.dialog);
    }

    public ExpectDialog(Context context, int themeResId) {
        this(context, themeResId, null);
    }

    public ExpectDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected ExpectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public ExpectDialog setListener(OnCloseListener listener) {
        this.listener = listener;
        return this;
    }

    public ExpectDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public ExpectDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public ExpectDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_expect);
        setCanceledOnTouchOutside(false);
        initView();
        initSpinner();
    }

    private void initView(){
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerExpect = findViewById(R.id.spinnerExpect);

        titleTxt = findViewById(R.id.title);
        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        etValue = findViewById(R.id.etValue);

        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
        }

        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }

        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }
    }

    private void initSpinner() {
        List<String> showCourseText = courseList.stream().map(CourseResponse::getCourseName).collect(Collectors.toList());
        ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, showCourseText);  //创建一个数组适配器
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapterCourse);
        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseId = courseList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> showExpectText = expectNameList.stream().map(ExpectNameResponse::getExpectName).collect(Collectors.toList());
        ArrayAdapter<String> adapterExpect = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, showExpectText);  //创建一个数组适配器
        adapterExpect.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpect.setAdapter(adapterExpect);
        spinnerExpect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expectNameId = expectNameList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setCourseList(List<CourseResponse> courseList){
        this.courseList = courseList;
    }

    public void setExpectNameList(List<ExpectNameResponse> expectNameList){
        this.expectNameList = expectNameList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(this, false, courseId, expectNameId, etValue.getText().toString().trim());
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(listener != null){
                    listener.onClick(this, true, courseId, expectNameId, etValue.getText().toString().trim());
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm, Integer courseId, Integer expectNameId, String value);
    }

}

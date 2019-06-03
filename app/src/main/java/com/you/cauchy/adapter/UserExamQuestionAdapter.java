package com.you.cauchy.adapter;

import android.content.Context;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.constant.Constant;
import com.you.cauchy.net.response.ExamDetailResponse;
import com.you.cauchy.net.response.UserExamDetailResponse;

public class UserExamQuestionAdapter extends CommRecyclerAdapter<UserExamDetailResponse.UserExamQuestion> {

    public UserExamQuestionAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mList || mList.size() == 0)
            return super.getItemViewType(position);
        else {
            Integer type = Constant.examQuestionType.get(mList.get(position).getType());
            return type == null ? 0 : type;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        int id;
        switch (viewType){
            case 0: id = R.layout.item_question_single; break;
            case 1: id = R.layout.item_question_multiple; break;
            case 2: id = R.layout.item_question_tf; break;
            case 3: id = R.layout.item_question_describe; break;
            default: id = R.layout.item_question_describe; break;
        }
        return id;
    }

    @Override
    public void convertView(ViewHolder holder, UserExamDetailResponse.UserExamQuestion question, int position) {
        holder.setText(R.id.tvQuestionTitle, (position+1) + "." + question.getQuestionContent());
        holder.setVisible(R.id.llTips, true);
        switch (question.getType()){
            case "single": loadSingle(holder, question, position); break;
            case "multiple": loadMultiple(holder, question, position); break;
            case "true_or_false": loadTf(holder, question, position); break;
            case "describe": loadDescribe(holder, question, position); break;
            default: break;
        }
        holder.setText(R.id.tvScore, question.getScore().toString());
        if (!"describe".equals(question.getType())){
            holder.setText(R.id.tvMyAnswer, question.getAnswer());
            holder.setText(R.id.tvCorrectAnswer, question.getCorrectAnswer());

            if (question.getCorrectAnswer().equals(question.getAnswer()))
                holder.setTextColorRes(R.id.tvMyAnswer, R.color.colorPrimary);
            else
                holder.setTextColorRes(R.id.tvMyAnswer, R.color.colorRed);
        }
    }

    private void loadSingle(ViewHolder holder, UserExamDetailResponse.UserExamQuestion question, int position){
        holder.setText(R.id.optionA, "A: "+question.getOptionA());
        holder.setText(R.id.optionB, "B: "+question.getOptionB());
        holder.setText(R.id.optionC, "C: "+question.getOptionC());
        holder.setText(R.id.optionD, "D: "+question.getOptionD());

        switch (question.getAnswer()){
            case "A": holder.setChecked(R.id.optionA, true);break;
            case "B": holder.setChecked(R.id.optionB, true);break;
            case "C": holder.setChecked(R.id.optionC, true);break;
            case "D": holder.setChecked(R.id.optionD, true);break;
            default:break;
        }
    }
    private void loadMultiple(ViewHolder holder, UserExamDetailResponse.UserExamQuestion question, int position){
        holder.setText(R.id.optionA, "A: "+question.getOptionA());
        holder.setText(R.id.optionB, "B: "+question.getOptionB());
        holder.setText(R.id.optionC, "C: "+question.getOptionC());
        holder.setText(R.id.optionD, "D: "+question.getOptionD());

        if (question.getAnswer().contains("A"))
            holder.setChecked(R.id.optionA, true);
        if (question.getAnswer().contains("B"))
            holder.setChecked(R.id.optionB, true);
        if (question.getAnswer().contains("C"))
            holder.setChecked(R.id.optionC, true);
        if (question.getAnswer().contains("D"))
            holder.setChecked(R.id.optionD, true);
    }
    private void loadTf(ViewHolder holder, UserExamDetailResponse.UserExamQuestion question, int position){
        switch (question.getAnswer()){
            case "True": holder.setChecked(R.id.optionTrue, true);break;
            case "False": holder.setChecked(R.id.optionFalse, true);break;
            default:break;
        }
    }
    private void loadDescribe(ViewHolder holder, UserExamDetailResponse.UserExamQuestion question, int position){
        holder.setText(R.id.etContent, question.getAnswer());
    }
}

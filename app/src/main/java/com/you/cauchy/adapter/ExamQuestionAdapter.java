package com.you.cauchy.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.constant.Constant;
import com.you.cauchy.net.response.ExamDetailResponse;

public class ExamQuestionAdapter extends CommRecyclerAdapter<ExamDetailResponse.ExaminationQuestion> {

    public ExamQuestionAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
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
    public void convertView(ViewHolder holder, ExamDetailResponse.ExaminationQuestion question, int position) {
        holder.setText(R.id.tvQuestionTitle, (position+1) + "." + question.getQuestionContent());
        switch (question.getType()){
            case "single": loadSingle(holder, question, position); break;
            case "multiple": loadMultiple(holder, question, position); break;
            case "true_or_false": loadTf(holder, question, position); break;
            case "describe": loadDescribe(holder, question, position); break;
            default: break;
        }
    }

    private void loadSingle(ViewHolder holder, ExamDetailResponse.ExaminationQuestion question, int position){
        holder.setText(R.id.optionA, "A: "+question.getOptionA());
        holder.setText(R.id.optionB, "B: "+question.getOptionB());
        holder.setText(R.id.optionC, "C: "+question.getOptionC());
        holder.setText(R.id.optionD, "D: "+question.getOptionD());
    }
    private void loadMultiple(ViewHolder holder, ExamDetailResponse.ExaminationQuestion question, int position){
        holder.setText(R.id.optionA, "A: "+question.getOptionA());
        holder.setText(R.id.optionB, "B: "+question.getOptionB());
        holder.setText(R.id.optionC, "C: "+question.getOptionC());
        holder.setText(R.id.optionD, "D: "+question.getOptionD());
    }
    private void loadTf(ViewHolder holder, ExamDetailResponse.ExaminationQuestion question, int position){
//        holder.setText(R.id.optionTrue, question.getOptionTrue());
//        holder.setText(R.id.optionFalse, question.getOptionFalse());
    }
    private void loadDescribe(ViewHolder holder, ExamDetailResponse.ExaminationQuestion question, int position){
    }
}

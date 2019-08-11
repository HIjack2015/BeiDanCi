package cn.jk.beidanci.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.jk.beidanci.R;
import cn.jk.beidanci.data.Constant;
import cn.jk.beidanci.data.model.CommonQuestion;

/**
 * Created by Administrator on 2017/6/30.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    List<CommonQuestion> questions;
    Context context;

    public QuestionAdapter(List<CommonQuestion> commonQuestions, Context context) {
        this.questions = commonQuestions;
        this.context = context;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_question, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {
        final CommonQuestion commonQuestion = questions.get(position);
        holder.questionTxt.setText(commonQuestion.getQuestion());
        ((View) holder.questionTxt.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SolutionActivity.class);
                intent.putExtra(Constant.QUESTION, commonQuestion);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTxt = itemView.findViewById(R.id.questionTxt);
        }
    }
}

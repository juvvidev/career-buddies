package com.juvvi.careerbuddies;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class QuestionAdapter extends FirestoreRecyclerAdapter<Question, QuestionAdapter.MyViewHolder> {


    public QuestionAdapter(@NonNull FirestoreRecyclerOptions<Question> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Question model) {

        holder.questionTv.setText("Q : " +model.getQuestion());
        CharSequence dateCharSeq = DateFormat.format("EEEE, MMM d, yyyy h:mm:ss a", model.getTimestamp().toDate());
        holder.timeTv.setText(dateCharSeq);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question snapshot = getSnapshots().get(holder.getAbsoluteAdapterPosition());

                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottomsheetlayout2);

                LinearLayout layoutAns = dialog.findViewById(R.id.layoutAnswer);
                LinearLayout layoutViewAns = dialog.findViewById(R.id.layoutViewAns);

                layoutAns.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "share your answer", Toast.LENGTH_SHORT).show();
                    }
                });

                layoutViewAns.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "view Answers", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);


            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent,false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView questionTv,tagTv,timeTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTv = itemView.findViewById(R.id.questionTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}

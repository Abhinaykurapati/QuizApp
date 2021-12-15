package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class questions_activity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private TextView questions,number_indicator;
    private FloatingActionButton save;
    private LinearLayout optionsContainer;
    private Button share,next;
    private int count=0,position=0;
    private List<QuestionModal> list;
    private int score=0;
    private String category;
    private int setNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar;
        toolbar=findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        questions=findViewById(R.id.question);
        number_indicator=findViewById(R.id.question_indicator);
        save=findViewById(R.id.floatingActionButton);
        optionsContainer=findViewById(R.id.options_container);
        share=findViewById(R.id.Share);
        next=findViewById(R.id.next);
        category=getIntent().getStringExtra("category");
        setNo=getIntent().getIntExtra("setNo",1);
       list=new ArrayList<>();
       myRef.child("SETS").child(category).child("questions").orderByChild("setNO").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot snapshot:dataSnapshot.getChildren())
               {
                   list.add(snapshot.getValue(QuestionModal.class));
               }
               if(list.size()>0)
               {

                   for(int i=0;i<4;i++)
                   {
                       optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               checkAnswer((Button) view);
                           }
                       });
                   }
                   playanim(questions,0,list.get(position).getQuestion());
                   next.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           next.setEnabled(false);
                           next.setAlpha(0.7f);
                           enableOption(true);
                           position++;
                           if(position == list.size())
                           {
                               return;
                           }
                           count=0;
                           playanim(questions,0,list.get(position).getQuestion());
                       }
                   });
               }
               else
               {
                   finish();
                   Toast.makeText(questions_activity.this, "no questions", Toast.LENGTH_SHORT).show();
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(questions_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

           }
       });
       /* number_indicator.setText(position+1+"/"+ list.size());*/


    }
    private void playanim(final View view, final int value,final String data)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                if(value == 0 && count<4)
                {
                    String option="";
                    if(count == 0)
                    {
                        option = list.get(position).getOptionA();
                    }
                    else if(count==1)
                    {
                        option = list.get(position).getOptionB();
                    }
                    else if(count==2)
                    {
                        option = list.get(position).getOptionC();
                    }
                    else if(count==3)
                    {
                        option = list.get(position).getOptionD();
                    }
                    playanim(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //data change
                if(value==0)
                {
                    try {
                        ((TextView)view).setText(data);
                        number_indicator.setText(position+1+"/"+ list.size());
                    }catch (ClassCastException ex)
                    {
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playanim(view,1,data);
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private void checkAnswer(Button selectedoption){
        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if(selectedoption.getText().toString().equals(list.get(position).getCorrectAnswer()))
        {//correct
            score++;
            selectedoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
        else
        {//incorrect
            selectedoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correct=(Button)optionsContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correct.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }
    private void enableOption(boolean enable)
    {
        for(int j=0;j<4;j++)
        {
            optionsContainer.getChildAt(j).setEnabled(enable);

        }
    }
}
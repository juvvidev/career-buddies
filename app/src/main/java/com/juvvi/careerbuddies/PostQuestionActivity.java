package com.juvvi.careerbuddies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostQuestionActivity extends AppCompatActivity {

    EditText text;
    Button button;
    Spinner spinner;

    String question,tag;
    FirebaseFirestore firestore;
    ArrayAdapter<String> arrayAdapter;
    String[] paths = {"Software","Engineering","Medical","Social","College"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);

        text = findViewById(R.id.quesEditText);
        button = findViewById(R.id.postBtn);
        spinner = findViewById(R.id.spinner);
        firestore = FirebaseFirestore.getInstance();

        arrayAdapter = new ArrayAdapter<>(PostQuestionActivity.this, android.R.layout.simple_spinner_dropdown_item,paths );
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tag = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tag = "Others";
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question = text.getText().toString();
                if (question.trim() != null) {

                    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Question questionModel = new Question(question,tag,new Timestamp(new Date()),userid);

                    firestore.collection("Queries")
                            .add(questionModel)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(PostQuestionActivity.this, "posted successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PostQuestionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

}
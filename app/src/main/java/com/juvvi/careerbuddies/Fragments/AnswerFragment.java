package com.juvvi.careerbuddies.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.juvvi.careerbuddies.Question;
import com.juvvi.careerbuddies.QuestionAdapter;
import com.juvvi.careerbuddies.R;

public class AnswerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "AnswerFragment";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    QuestionAdapter questionAdapter;


    public AnswerFragment() {
        // Required empty public constructor
    }

    public static AnswerFragment newInstance(String param1, String param2) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initRecyclerView() {

        Query query = FirebaseFirestore.getInstance()
                .collection("Queries");


        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question.class)
                .build();

        questionAdapter = new QuestionAdapter(options);
        recyclerView.setAdapter(questionAdapter);
        questionAdapter.startListening();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        Log.d(TAG, "onCreateView: ");
        initRecyclerView();
        return view;
    }
}
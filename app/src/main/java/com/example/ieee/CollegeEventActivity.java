package com.example.ieee;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CollegeEventActivity extends AppCompatActivity {

    private RecyclerView proceduresRecyclerList;
    private CollegeEventsAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_event);

        proceduresRecyclerList = findViewById(R.id.collegeEventsRecyclerView);
        proceduresRecyclerList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String collegeName = intent.getStringExtra("collegeName");

        assert collegeName != null;
        reference = FirebaseDatabase.getInstance().getReference("Events").child(collegeName);

        FirebaseRecyclerOptions<CollegeEvents> options =
                new FirebaseRecyclerOptions.Builder<CollegeEvents>()
                        .setQuery(reference, CollegeEvents.class)
                        .build();
        adapter = new CollegeEventsAdapter(options, this);
        proceduresRecyclerList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
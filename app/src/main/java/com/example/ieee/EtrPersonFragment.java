package com.example.ieee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EtrPersonFragment extends Fragment {

    private RecyclerView proceduresRecyclerList;
    private ETRPersonAdapter adapter;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etr_person, container, false);

        proceduresRecyclerList = view.findViewById(R.id.etrPersonsRecyclerView);
        proceduresRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));

        reference = FirebaseDatabase.getInstance().getReference("ETRPersonsDetails");

        FirebaseRecyclerOptions<ETRPerson> options =
                new FirebaseRecyclerOptions.Builder<ETRPerson>()
                        .setQuery(reference, ETRPerson.class)
                        .build();
        adapter = new ETRPersonAdapter(options, getContext());
        proceduresRecyclerList.setAdapter(adapter);

        return view;
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
package com.example.ieee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class EventsFragment extends Fragment {

    Spinner clgList, collegeList;
    List<String> allClgList = new ArrayList<>();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("CollegeList");
    Button seeCollege, searchCollege;
    TextView getDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        clgList = view.findViewById(R.id.collegeList);
        collegeList = view.findViewById(R.id.selectCollegeList);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String college = ds.child("clgName").getValue(String.class);
                    allClgList.add(college);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, allClgList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clgList.setAdapter(arrayAdapter);
                collegeList.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seeCollege = view.findViewById(R.id.seeSelectedCollegeEvents);
        searchCollege = view.findViewById(R.id.seeSelectedCollegeEventsAsPerDate);

        seeCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CollegeEventActivity.class);
                intent.putExtra("collegeName", clgList.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        getDate = view.findViewById(R.id.eventDate);
        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datepicker = new DatePickerDialog(Objects.requireNonNull(getContext()),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                getDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        searchCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String college = collegeList.getSelectedItem().toString();
                String selectedDate = getDate.getText().toString();

                if (selectedDate.isEmpty()){
                    Toast.makeText(getContext(), "Please select a date first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events").child(college);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String date = ds.child("eventDate").getValue(String.class);
                            String title = ds.child("eventTitle").getValue(String.class);
                            assert date != null;
                            if (date.equals(selectedDate)){
                                Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Not Found Any Event", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        return view;
    }
}
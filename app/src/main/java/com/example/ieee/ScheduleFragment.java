package com.example.ieee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    TextInputLayout title, desc, location;
    Button sMeet;
    Spinner etrPerson;
    List<String> etrPersonList = new ArrayList<String>();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("ETRPersonsDetails");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        title = view.findViewById(R.id.meetingTitle);
        desc = view.findViewById(R.id.meetingDescription);
        location = view.findViewById(R.id.meetingLocation);

        etrPerson = view.findViewById(R.id.chooseETRPerson);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String consultaName = areaSnapshot.child("name").getValue(String.class);
                    etrPersonList.add(consultaName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, etrPersonList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                etrPerson.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sMeet = view.findViewById(R.id.scheduleMeeting);
        sMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mTitle = title.getEditText().getText().toString().trim();
                String mDesc = desc.getEditText().getText().toString().trim();
                String mLocate = location.getEditText().getText().toString().trim();

                if (mTitle.isEmpty()){
                    title.setError("Please enter a title here");
                    return;
                }
                if (mDesc.isEmpty()){
                    desc.setError("Please enter a description here");
                    return;
                }
                if (mLocate.isEmpty()){
                    location.setError("Please enter a location here");
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, mTitle);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, mDesc);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, mLocate);
                intent.putExtra(CalendarContract.Events.ALL_DAY, true);
//                intent.putExtra(CalendarContract.Events., "true");
//                intent.putExtra(Intent.EXTRA_EMAIL, "test@gamil.com, test1@gmail.com, test2@gmail.com");

                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "There is no app that can support this action", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
package com.example.ieee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private TextView txtFName, txtMName, txtLName, txtClgName, txtEmail, txtSQuestion;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtFName = view.findViewById(R.id.showFirstName);
        txtMName = view.findViewById(R.id.showMiddleName);
        txtLName = view.findViewById(R.id.showLastName);
        txtClgName = view.findViewById(R.id.showCollegeName);
        txtEmail = view.findViewById(R.id.showEmailAddress);
        txtSQuestion = view.findViewById(R.id.showSecurityQuestion);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("CommitteeMember").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                committeeMember users = snapshot.getValue(committeeMember.class);
                txtFName.setText(users.getfName());
                txtMName.setText(users.getmName());
                txtLName.setText(users.getlName());
                txtClgName.setText(users.getCollegeName());
                txtEmail.setText(users.getEmailAdd());
                txtSQuestion.setText(users.getsQuestion());

//                if (users.getImageURL().equals("default")){
//                    profileImage.setImageResource(R.drawable.profile);
//                }else {
//                    Glide.with(ProfileActivity.this).load(users.getImageURL()).into(profileImage);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
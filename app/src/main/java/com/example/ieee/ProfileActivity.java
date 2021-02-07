package com.example.ieee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtFullName, txtEmail, txtMobile, txtExpertise, txtQualification;
    private ImageView profileImage;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userName");

        txtFullName = findViewById(R.id.showFullName);
        txtEmail = findViewById(R.id.showEmailID);
        txtMobile = findViewById(R.id.showMobileNumber);
        txtExpertise = findViewById(R.id.showExpertise);
        txtQualification = findViewById(R.id.showQualification);

        profileImage = findViewById(R.id.profileImageOne);

        reference = FirebaseDatabase.getInstance().getReference("ETRPersonsDetails").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ETRPerson etrPerson = snapshot.getValue(ETRPerson.class);
                txtFullName.setText(etrPerson.getName());
                txtEmail.setText(etrPerson.getEmailAdd());
                txtMobile.setText(etrPerson.getMobileNumber());
                txtExpertise.setText(etrPerson.getExpertise());
                txtQualification.setText(etrPerson.getQualification());

                if (etrPerson.getImageURL().equals("notyet")){
                    profileImage.setImageResource(R.drawable.boss);
                }
//                else {
//                    Glide.with(ProfileActivity.this).load(users.getImageURL()).into(profileImage);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

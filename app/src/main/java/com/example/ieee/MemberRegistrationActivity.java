package com.example.ieee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MemberRegistrationActivity extends AppCompatActivity {

    private Spinner typeSpinner, sqSpinner;
    private TextInputLayout fName, mName, lName, email, password, cpassword;
    private TextInputEditText sqAnswer;
    private ProgressDialog progressDialog;
    String userID;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_registration);

        mAuth = FirebaseAuth.getInstance();

        typeSpinner = findViewById(R.id.joinMemberType);
        sqSpinner = findViewById(R.id.securityQuestionType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.securityQuestions, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sqSpinner.setAdapter(adapter1);

        fName = findViewById(R.id.memberFirstName);
        mName = findViewById(R.id.memberMiddleName);
        lName = findViewById(R.id.memberLastName);
        email = findViewById(R.id.memberEmail);
        password = findViewById(R.id.memberPassword);
        cpassword = findViewById(R.id.memberConfirmPassword);

        sqAnswer = findViewById(R.id.securityQuestionAnswer);
        progressDialog = new ProgressDialog(this);

        Button register = findViewById(R.id.memberRegisteredButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String joinType = typeSpinner.getSelectedItem().toString().trim();
                final String firstName = Objects.requireNonNull(fName.getEditText()).getText().toString().trim();
                final String middleName = Objects.requireNonNull(mName.getEditText()).getText().toString().trim();
                final String lastName = Objects.requireNonNull(lName.getEditText()).getText().toString().trim();
                final String emailID = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
                final String pass = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
                final String cpass = Objects.requireNonNull(cpassword.getEditText()).getText().toString().trim();
                final String sQuest = sqSpinner.getSelectedItem().toString().trim();
                final String sqAns = Objects.requireNonNull(sqAnswer.getText()).toString().trim();

                if (firstName.isEmpty()){
                    fName.setError("Given/First Name is required");
                    return;
                }
                if (middleName.isEmpty()){
                    mName.setError("Middle Name is required");
                    return;
                }
                if (lastName.isEmpty()){
                    lName.setError("Last/Surname is required");
                    return;
                }
                if (emailID.isEmpty()){
                    email.setError("Email Address is required");
                    return;
                }
                if (pass.isEmpty() || pass.length() < 6){
                    password.setError("Password must be greater than 6 characters");
                    return;
                }
                if (cpass.isEmpty() || cpass.length() < 6) {
                    cpassword.setError("Confirm Password must be greater than 6 characters");
                    return;
                }
                if (!(pass.equals(cpass))){
                    Toast.makeText(MemberRegistrationActivity.this, "Password and Confirm Password doesn't match, Please try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sqAns.isEmpty()){
                    Toast.makeText(MemberRegistrationActivity.this, "Security Question Answer is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Membership Registration");
                progressDialog.setMessage("Please wait while registration gets complete..");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(emailID, pass)
                        .addOnCompleteListener(MemberRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            ieeeMember ieeeMember = new ieeeMember(joinType,
                                    firstName,
                                    middleName,
                                    lastName,
                                    emailID,
                                    pass,
                                    cpass,
                                    sQuest,
                                    sqAns,
                                    userID);

                            myRef.child(userID).setValue(ieeeMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MemberRegistrationActivity.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MemberRegistrationActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(MemberRegistrationActivity.this, "Error:"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
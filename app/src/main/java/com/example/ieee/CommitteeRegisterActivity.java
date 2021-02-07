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

public class CommitteeRegisterActivity extends AppCompatActivity {

    private Spinner sqSpinner;
    private TextInputLayout fName, mName, lName, email, password, cpassword, clgName;
    private TextInputEditText sqAnswer;
    private ProgressDialog progressDialog;
    String userID, verification;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("CommitteeMember");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_register);

        mAuth = FirebaseAuth.getInstance();

        sqSpinner = findViewById(R.id.cSecurityQuestionType);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.securityQuestions, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sqSpinner.setAdapter(adapter1);

        fName = findViewById(R.id.cmemberFirstName);
        mName = findViewById(R.id.cmemberMiddleName);
        lName = findViewById(R.id.cmemberLastName);
        email = findViewById(R.id.cmemberEmail);
        password = findViewById(R.id.cmemberPassword);
        cpassword = findViewById(R.id.cmemberConfirmPassword);
        clgName = findViewById(R.id.cmemberCollegeName);

        sqAnswer = findViewById(R.id.cSecurityQuestionAnswer);
        progressDialog = new ProgressDialog(this);

        Button register = findViewById(R.id.cMemberRegisteredButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstName = Objects.requireNonNull(fName.getEditText()).getText().toString().trim();
                final String middleName = Objects.requireNonNull(mName.getEditText()).getText().toString().trim();
                final String lastName = Objects.requireNonNull(lName.getEditText()).getText().toString().trim();
                final String collegeName = Objects.requireNonNull(clgName.getEditText()).getText().toString().trim();
                final String emailID = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
                final String pass = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
                final String cpass = Objects.requireNonNull(cpassword.getEditText()).getText().toString().trim();
                final String sQuest = sqSpinner.getSelectedItem().toString().trim();
                final String sqAns = Objects.requireNonNull(sqAnswer.getText()).toString().trim();
                verification = "not verified";

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
                if (collegeName.isEmpty()){
                    clgName.setError("College Name is required");
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
                    Toast.makeText(CommitteeRegisterActivity.this, "Password and Confirm Password doesn't match, Please try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sqAns.isEmpty()){
                    Toast.makeText(CommitteeRegisterActivity.this, "Security Question Answer is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Committee Membership Registration");
                progressDialog.setMessage("Please wait while registration gets complete..");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(emailID, pass)
                        .addOnCompleteListener(CommitteeRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                    committeeMember committeeMember = new committeeMember(firstName,
                                            middleName,
                                            lastName,
                                            emailID,
                                            pass,
                                            cpass,
                                            sQuest,
                                            sqAns,
                                            userID,
                                            verification,
                                            collegeName);

                                    myRef.child(userID).setValue(committeeMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(CommitteeRegisterActivity.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CommitteeRegisterActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    });
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(CommitteeRegisterActivity.this, "Error:"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
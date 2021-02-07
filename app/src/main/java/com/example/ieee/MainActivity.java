package com.example.ieee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView tvRegular;
    private TextView tvCommittee;
    private CardView regularCV, committeeCV;
    TextView memberRegistration, committeeRegistration;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference cRef = firebaseDatabase.getReference("CommitteeMember");
    DatabaseReference imRef = firebaseDatabase.getReference("Users");
    ProgressDialog progressDialog;

    private TextInputLayout cEmail, cPassword, imEmail, imPassword;
    private MaterialTextView cForget, imForget;
    private Button cLogin, imLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        tvRegular = findViewById(R.id.regularMember);
        tvCommittee = findViewById(R.id.commiteeMember);
        regularCV = findViewById(R.id.regularCardView);
        committeeCV = findViewById(R.id.commiteeCardView);
        memberRegistration = findViewById(R.id.regularMemberRegister);
        committeeRegistration = findViewById(R.id.committeeMemberRegister);

        tvRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRegular.setTextColor(getResources().getColor(R.color.black));
                tvRegular.setBackgroundResource(R.drawable.login_card_back);

                tvCommittee.setTextColor(getResources().getColor(R.color.white));
                tvCommittee.setBackgroundResource(0);

                committeeCV.setVisibility(View.GONE);
                regularCV.setVisibility(View.VISIBLE);
            }
        });

        tvCommittee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCommittee.setTextColor(getResources().getColor(R.color.black));
                tvCommittee.setBackgroundResource(R.drawable.login_card_back);

                tvRegular.setTextColor(getResources().getColor(R.color.white));
                tvRegular.setBackgroundResource(0);

                regularCV.setVisibility(View.GONE);
                committeeCV.setVisibility(View.VISIBLE);
            }
        });

        memberRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MemberRegistrationActivity.class));
            }
        });

        committeeRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommitteeRegisterActivity.class));
            }
        });

        cLogin = findViewById(R.id.commiteeLoginButton);

        cEmail = findViewById(R.id.commiteeLoginEmail);
        cPassword = findViewById(R.id.commiteeLoginPassword);
        imEmail = findViewById(R.id.regularLoginEmail);
        imPassword = findViewById(R.id.regularLoginPassword);

        cForget = findViewById(R.id.commiteeForgotPassword);
        imForget = findViewById(R.id.regularForgotPassword);
        imLogin = findViewById(R.id.regularLoginButton);

        progressDialog = new ProgressDialog(this);

        cLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = cEmail.getEditText().getText().toString().trim();
                String password = cPassword.getEditText().getText().toString().trim();

                if (email.isEmpty()){
                    cEmail.setError("Email Address is required");
                    return;
                }
                if (password.isEmpty() || password.length() < 6){
                    cPassword.setError("Password must be greater than 6 characters");
                    return;
                }

                progressDialog.setTitle("IEEE Application");
                progressDialog.setMessage("Please wait while getting you logged in..");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this, AfterCommitteeLoginActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        imLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = imEmail.getEditText().getText().toString().trim();
                String password = imPassword.getEditText().getText().toString().trim();

                if (email.isEmpty()){
                    imEmail.setError("Email Address is required");
                    return;
                }
                if (password.isEmpty() || password.length() < 6){
                    imPassword.setError("Password must be greater than 6 characters");
                    return;
                }

                progressDialog.setTitle("IEEE Application");
                progressDialog.setMessage("Please wait while getting you logged in..");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this, AfterMemberLoginActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, AfterCommitteeLoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("IEEE Application")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
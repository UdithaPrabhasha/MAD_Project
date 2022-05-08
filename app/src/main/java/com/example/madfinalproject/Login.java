package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private TextInputEditText edtEmail, edtPassword;
    private TextInputLayout edtEmailLayout, edtPwdLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Customer");

        edtEmail = findViewById(R.id.signinEmailTextField);
        edtPassword = findViewById(R.id.signinPasswordTextField);
        edtEmailLayout = findViewById(R.id.signinEmailLayout);
        edtPwdLayout = findViewById(R.id.signinPwdLayout);

    }

    public void onLoginBtnClicked(View view) {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        edtEmailLayout.setErrorEnabled(false);
        edtPwdLayout.setErrorEnabled(false);

        if (TextUtils.isEmpty(email)) {
            edtEmailLayout.setError("Email is required");
            edtEmailLayout.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailLayout.setError("Please provide a valid email");
            edtEmailLayout.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtPwdLayout.setError("Password is required");
            edtPwdLayout.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtPwdLayout.setError("Minimum password length is 6 characters");
            edtPwdLayout.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser customer = FirebaseAuth.getInstance().getCurrentUser();
                        String name = "Jerry";
                        userRef = FirebaseDatabase.getInstance().getReference("Customer");
                    } else {
                        Toast.makeText(Login.this, "Login failed! Please check email and password", Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> Login.super.onBackPressed()).create().show();
    }

    public void registerBtnClicked(View view) {
        startActivity(new Intent(Login.this, Customer.class));
    }
}
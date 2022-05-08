package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private TextInputEditText etEmail,etPassword;
    private Button btnSignIn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = (TextView) findViewById(R.id.textView4);
        register.setOnClickListener(this);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        etEmail = (TextInputEditText) findViewById(R.id.signinEmailTextField);
        etEmail.setOnClickListener(this);

        etPassword = (TextInputEditText) findViewById(R.id.signinPasswordTextField);
        etPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView4:
                startActivity(new Intent(this,MainActivity.class));
                break;

                case R.id.btnSignIn:
                    userLogin();
                    break;
            }
        }
        private void userLogin(){
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();


            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                return;
            }
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Please provide a valid email");
                etEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                etPassword.setError("Minimum password length is 6 characters");
                etPassword.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //redirect to user profile
                        startActivity(new Intent(SignIn.this, ProfileActivity.class));
                    }
                    else{
                        Toast.makeText(SignIn.this, "Failed to login", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }
}
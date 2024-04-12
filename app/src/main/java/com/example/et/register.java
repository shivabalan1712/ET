package com.example.et;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        buttonReg=findViewById(R.id.btn_register);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.click_here_to_login);

        buttonReg.setOnClickListener(v ->{
            String email,password;
            progressBar.setVisibility(View.VISIBLE);
            email=String.valueOf(editTextEmail.getText());
            password=String.valueOf(editTextPassword.getText());

            if(TextUtils.isEmpty(email)){
                Toast.makeText(register.this, "Enter Email", Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(register.this,"Enter Password", Toast.LENGTH_SHORT).show();
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Authentication successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        });
        textView.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();
        });

    }
}
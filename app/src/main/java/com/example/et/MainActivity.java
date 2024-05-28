package com.example.et;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db;
    Button button, dash, d2;
    TextView textView;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        button = findViewById(R.id.logout);
        dash = findViewById(R.id.dashboard);
        d2 = findViewById(R.id.dash_2);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        } else {
            fetchUserName(user.getUid());
        }

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        });

        dash.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        });

        d2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Expenses.class);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void fetchUserName(String uid) {
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String name = document.getString("name");
                    textView.setText("Welcome, " + name);
                } else {
                    textView.setText("No user data found");
                }
            } else {
                textView.setText("Failed to fetch user data");
            }
        });
    }
}

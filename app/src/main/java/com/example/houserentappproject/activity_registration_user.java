package com.example.houserentappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_registration_user extends AppCompatActivity {

    private EditText userFullName, userEmail, userPassword, userContact;
    private Button btn_register;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        userFullName = findViewById(R.id.id_fullname_newuser);
        userEmail = findViewById(R.id.id_email_newuser);
        userPassword = findViewById(R.id.id_password_newuser);
        userContact = findViewById(R.id.id_mobileno_newuser);
        btn_register = findViewById(R.id.btn_regitser);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userFullName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String mobileNo = userContact.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || 
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(mobileNo)) {
                    Toast.makeText(activity_registration_user.this, 
                        "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(activity_registration_user.this, 
                        "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity_registration_user.this, 
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Save additional user data
                                        UserData userData = new UserData(name, email, mobileNo);
                                        mDatabase.child(user.getUid()).setValue(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Registration successful
                                                        Intent intent = new Intent(
                                                            activity_registration_user.this,
                                                            activity_reg_successful.class);
                                                        intent.putExtra("name", name);
                                                        intent.putExtra("email", email);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(activity_registration_user.this,
                                                            "Failed to save user data: " + 
                                                            task.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    }
                                } else {
                                    // If registration fails, display a message to the user.
                                    Toast.makeText(activity_registration_user.this,
                                        "Registration failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // User data class for Firebase
    private static class UserData {
        public String name;
        public String email;
        public String mobileNo;

        public UserData() {
            // Default constructor required for Firebase
        }

        public UserData(String name, String email, String mobileNo) {
            this.name = name;
            this.email = email;
            this.mobileNo = mobileNo;
        }
    }
}
package com.example.houserentappproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class activity_login_user extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private SessionManager sessionManager;
    private FirebaseAuth mAuth;
    private EditText emailInput, passwordInput;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        
        // Initialize session manager
        sessionManager = new SessionManager(this);
        
        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            // User is already logged in, proceed to main activity
            startMainActivity();
            return;
        }

        setContentView(R.layout.activity_login_user);

        // Initialize views
        emailInput = findViewById(R.id.email_et);
        passwordInput = findViewById(R.id.password_et);
        signInButton = findViewById(R.id.btn_sign_in);
        ImageView img_google_sign_in = findViewById(R.id.img_google_sign_in);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set up email/password sign in
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(activity_login_user.this, "Please fill all fields", 
                        Toast.LENGTH_SHORT).show();
                    return;
                }
                
                signInWithEmailPassword(email, password);
            }
        });

        // Set up Google sign in
        img_google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
    }

    private void signInWithEmailPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Save login session
                            sessionManager.createLoginSession(
                                user.getEmail(),
                                user.getDisplayName(),
                                user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : ""
                            );
                            startMainActivity();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activity_login_user.this, "Authentication failed: " + 
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Save login session
                            sessionManager.createLoginSession(
                                user.getEmail(),
                                user.getDisplayName(),
                                user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : ""
                            );
                            startMainActivity();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activity_login_user.this, "Authentication failed: " + 
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void startMainActivity() {
        // Check if we have a saved session
        if (sessionManager.isLoggedIn()) {
            // If we have a saved session, go directly to city selection
            Intent intent = new Intent(this, activity_selectcity.class);
            startActivity(intent);
        } else {
            // For fresh login, go to successful sign-in screen
            Intent intent = new Intent(this, activity_successful_google_signin.class);
            startActivity(intent);
        }
        finish();
    }
}
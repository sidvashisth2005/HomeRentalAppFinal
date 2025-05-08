package com.example.houserentappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class activity_successful_google_signin extends AppCompatActivity {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut, continueButton;
    private GoogleSignInClient googleSignInClient;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_google_signin);

        sessionManager = new SessionManager(this);
        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        profileImage = findViewById(R.id.profile_image);
        signOut = findViewById(R.id.btn_sign_out);
        continueButton = findViewById(R.id.btn_continue);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        setDataOnView();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Proceed to city selection
                Intent intent = new Intent(activity_successful_google_signin.this, activity_selectcity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDataOnView() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        if (googleSignInAccount != null) {
            Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
            profileName.setText(googleSignInAccount.getDisplayName());
            profileEmail.setText(googleSignInAccount.getEmail());
            
            // Save session data
            sessionManager.createLoginSession(
                googleSignInAccount.getEmail(),
                googleSignInAccount.getDisplayName(),
                googleSignInAccount.getPhotoUrl() != null ? googleSignInAccount.getPhotoUrl().toString() : ""
            );
        }
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Clear session
                sessionManager.logout();
                
                // Navigate back to login
                Intent intent = new Intent(activity_successful_google_signin.this, activity_login_user.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
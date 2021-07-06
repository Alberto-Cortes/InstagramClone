package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    // Declaration of logic part of visual elements
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null){
            goToPostsActivity();
        }

        // Connect logic and visual parts of layout
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // Click handler for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from the views and pass it to login method
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
                Toast.makeText(LoginActivity.this, "Toast message", Toast.LENGTH_LONG).show();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            goToPostsActivity();
                        } else  {
                            Log.e(TAG, "Parse signup error", e);
                        }
                    }
                });
            }
        });
    }

    // Method to check Parse for valid login user
    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // If an exception occurs send a log
                if (e != null){
                    Log.e(TAG, "Parse login error", e);
                    return;
                }
                // No issues, launch posts activity
                goToPostsActivity();
            }
        });
    }

    private void goToPostsActivity() {
        Intent i = new Intent(LoginActivity.this, PostsActivity.class);
        startActivity(i);
        finish();
    }
}
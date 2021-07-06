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

public class LoginActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    // Declaration of logic part of visual elements
    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Connect logic and visual parts of layout
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btLogin = findViewById(R.id.btnLogin);

        // Click handler for login button
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from the views and pass it to login method
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
                Toast.makeText(LoginActivity.this, "Toast message", Toast.LENGTH_LONG).show();
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
                Intent i = new Intent(LoginActivity.this, PostsActivity.class);
                startActivity(i);
            }
        });
    }
}
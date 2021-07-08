package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

public class PostDetailActivity extends AppCompatActivity {

    // Declare logical part of visual components
    TextView tvDetailUsername;
    TextView tvDetailTimestamp;
    TextView tvDetailDescription;
    ImageView ivDetailPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // Connect visual and logical elements
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailUsername = findViewById(R.id.tvDetailUsername);
        tvDetailTimestamp = findViewById(R.id.tvDetailTimestamp);
        ivDetailPicture = findViewById(R.id.ivDetailPicture);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvDetailDescription.setText(post.getDescription());
        tvDetailTimestamp.setText(post.dateToTimestamp(post.getCreatedAt()));
        tvDetailUsername.setText(post.getUser().getUsername());

        Glide.with(this).load(post.getImage().getUrl()).into(ivDetailPicture);

    }
}
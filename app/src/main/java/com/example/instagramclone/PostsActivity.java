package com.example.instagramclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class PostsActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    // VIsual elements for capture activity
    private Button btnLogout;
    private EditText etPostDescription;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private Button btnCapture;
    private Button btnFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        btnLogout = findViewById(R.id.btnLogout);
        etPostDescription = findViewById(R.id.etPosttDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCapture = findViewById(R.id.btnCapture);
        ivPostImage = findViewById(R.id.ivPostImage);
        btnFeed = findViewById(R.id.btnFeed);

        // Give logout button functionality by logging user out and returning to LoginActivity
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent i = new Intent(PostsActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etPostDescription.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(PostsActivity.this, "Description can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivPostImage.getDrawable() == null){
                    Toast.makeText(PostsActivity.this, "Must have a photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile);
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });
        btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostsActivity.this, FeedActivity.class);
                startActivity(i);
            }
        });
    }

    private void launchCamera() {
        // Define implicit camera intent
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a file reference
        photoFile = getPhotoFileUri(photoFileName);

        // Wrap file on a content provider
        Uri fileProvider = FileProvider.getUriForFile(PostsActivity.this, "com.codepath.fileprovider", photoFile);

        // Add file provider to the intent
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If the phone has a camera, start the activity expecting a result
        if (i.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get image from disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivPostImage);
                ivPreview.setImageBitmap(takenImage);
            } else {
                // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String photoFileName) {
        // Save photos on phone's directory
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Error catching
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + photoFileName);

        return file;
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        // Define a new post and set its user and description
        Post post = new Post();
        post.setDescription(description);
        post.setUser(currentUser);
        post.setImage(new ParseFile(photoFile));


        // Save post to DB
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Parse save error", e);
                }
            }
        });
        etPostDescription.setText("");
        ivPostImage.setImageResource(0);
    }
}
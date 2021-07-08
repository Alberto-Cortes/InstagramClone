package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity implements PostsAdapter.PostInteractionListener {

    public final String TAG = this.getClass().getSimpleName();

    // Visual elements of the activity
    RecyclerView rvPosts;
    PostsAdapter adapter;
    List<Post> posts;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Logic and visual connection for RV
        rvPosts = findViewById(R.id.rvPosts);
        // Define posts as an empty array
        posts = new ArrayList<>();
        // Define the adapter for the RV, pass empty array of posts
        adapter = new PostsAdapter(this, posts, this);
        // Set the RV adapter with the one just created
        rvPosts.setAdapter(adapter);
        // Set a layout for the RV
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvPosts.setLayoutManager(linearLayoutManager);

        // Query posts from the DB
        queryPosts();

        // Connect visual and logic parts of the swipe container
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        // Query posts when swipe is done
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });

    }

    // Query posts from the DB
    private void queryPosts() {
        // Define a query for posts object
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Include data of self user
        query.include(Post.KEY_USER);
        // Limit quantity of items to be retrieved
        query.setLimit(20);
        // Find posts using the query
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                // Check for errors on query's answer
                if (e != null){
                    Log.e(TAG, "Parse query error", e);
                    return;
                }
                // Clear all posts from the array, then put the new ones inside,
                // notify adapter and stop refreshing animation
                posts.clear();
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    // Implementation of click listener for the post
    @Override
    public void onPostImageClick(int position) {
        Post post = posts.get(position);
        Intent i = new Intent(this, PostDetailActivity.class);
        i.putExtra("post", Parcels.wrap(post));
        //dialogFragment(Parcels.wrap(Post));
        startActivity(i);
    }
}
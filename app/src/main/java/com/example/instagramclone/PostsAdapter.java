package com.example.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    // Listener for post interactions
    public interface PostInteractionListener {
        void onPostImageClick(int position);
    }

    PostInteractionListener listener;

    // Context and items for the RV
    Context context;
    List<Post> posts;

    // Constructor fot the adapter
    public PostsAdapter(Context context, List<Post> posts, PostInteractionListener postInteractionListener) {
        this.context = context;
        this.posts = posts;
        this.listener = postInteractionListener;
    }

    // Inflate layout for each row when created
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    // Bind data of post to holder
    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    // Get size of data array inside the RV
    @Override
    public int getItemCount() {
        return posts.size();
    }



    // Holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        // VIsual elements of each row
        TextView tvUsername;
        ImageView ivImage;
        TextView tvDescription;
        TextView tvPostTimestamp;

        // Match visual and logic counterparts
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvPostTimestamp = itemView.findViewById(R.id.tvPostTimestamp);
        }

        // Extract data from post and bind it to a row
        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvPostTimestamp.setText(post.dateToTimestamp(post.getCreatedAt()));
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //PostInteractionListener
                    listener.onPostImageClick(getAdapterPosition());
                }
            });
        }
    }
}

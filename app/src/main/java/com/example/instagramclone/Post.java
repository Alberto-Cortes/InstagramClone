package com.example.instagramclone;

import android.text.format.DateUtils;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    // Strings to be used for interactions with Parse's post DB
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    // Getters for each attribute of a post
    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }


    // Setters for each attribute of a post
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }
    public void setImage(ParseFile file){
        put(KEY_IMAGE, file);
    }
    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    // Using already implemented tools to get relative timestamp
    public String dateToTimestamp(Date date) {
        return (String) DateUtils.getRelativeTimeSpanString(date.getTime());
    }
}

package com.example.domainobjects;

import com.example.memecards.R;

import java.lang.reflect.Field;

public class MemeCard {
    private String name;
    private String description;
    private String filename;
    private int upvotes;
    private boolean locked;

    public MemeCard(String name, String description, String filename, int upvotes, boolean locked) {
        this.name = name;
        this.description = description;
        this.filename = filename;
        this.upvotes = upvotes;
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFilename() {
        return filename;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setLocked(boolean locked){
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getUpvotesStr(){
        return this.upvotes/1000 + "." + (this.upvotes%1000)/100 + " k";
    }

    // gets the resource id of a card image given its name.
    public int getResId(){
        try {
            String filename = this.filename.split("\\.")[0];
            Field idField = R.drawable.class.getDeclaredField(filename);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
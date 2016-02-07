package com.codepath.kpu.pix.models;

import java.util.ArrayList;

/**
 * Created by kpu on 2/6/16.
 */
public class IGPost implements Comparable<IGPost> {
    public long createdTime;
    public IGUser author;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public ArrayList<IGPostComment> comments;

    @Override
    public int compareTo(IGPost other) {
        long a = this.createdTime;
        long b = other.createdTime;
        return a < b ? -1 : (a == b ? 0 : 1);
    }
}

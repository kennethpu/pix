package com.codepath.kpu.pix.models;

import java.util.ArrayList;

/**
 * Created by kpu on 2/6/16.
 */
public class IGPost {
    public double createdTime;
    public IGUser author;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public ArrayList<IGPostComment> comments;
}

package com.codepath.kpu.pix.network;

import com.codepath.kpu.pix.models.IGPost;
import com.codepath.kpu.pix.models.IGPostComment;
import com.codepath.kpu.pix.models.IGUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kpu on 2/6/16.
 */
public class IGPostResponseSerializer {

    public static ArrayList<IGPost> handleFetchPopularPostsResponse(JSONObject response) {
        JSONArray postsJSON = response.optJSONArray(IGPostConstants.IG_DATA);
        ArrayList<IGPost> posts = getPostsFromJSONArray(postsJSON);
        return posts;
    }

    private static ArrayList<IGPost> getPostsFromJSONArray(JSONArray postsJSON) {
        ArrayList<IGPost> posts = new ArrayList<>();
        if (postsJSON != null) {
            for (int i = 0; i < postsJSON.length(); i++) {
                JSONObject postJSON = postsJSON.optJSONObject(i);
                IGPost post = getPostFromJSON(postJSON);
                if (post != null) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    private static IGPost getPostFromJSON(JSONObject postJSON) {
        IGPost post = new IGPost();
        if (postJSON != null) {
            post.createdTime = postJSON.optLong(IGPostConstants.IG_POST_CREATED_TIME);

            JSONObject userJSON = postJSON.optJSONObject(IGPostConstants.IG_POST_AUTHOR);
            post.author = getUserFromJSON(userJSON);

            JSONObject captionJSON = postJSON.optJSONObject(IGPostConstants.IG_POST_CAPTION);
            if (captionJSON != null) {
                post.caption = captionJSON.optString(IGPostConstants.IG_POST_CAPTION_TEXT);
            }

            JSONObject imagesJSON = postJSON.optJSONObject(IGPostConstants.IG_POST_IMAGES);
            if (imagesJSON != null) {
                JSONObject imageJSON = imagesJSON.optJSONObject(IGPostConstants.IG_POST_IMAGES_STANDARD);
                if (imageJSON != null) {
                    post.imageUrl = imageJSON.optString(IGPostConstants.IG_POST_IMAGE_URL);
                    post.imageHeight = imageJSON.optInt(IGPostConstants.IG_POST_IMAGE_HEIGHT);
                }
            }

            JSONObject likesJSON = postJSON.optJSONObject(IGPostConstants.IG_POST_LIKES);
            if (likesJSON != null) {
                post.likesCount = likesJSON.optInt(IGPostConstants.IG_POST_LIKES_COUNT);
            }

            JSONObject commentsJSON = postJSON.optJSONObject(IGPostConstants.IG_POST_COMMENTS);
            if (commentsJSON != null) {
                JSONArray commentsJSONArray = commentsJSON.optJSONArray(IGPostConstants.IG_POST_COMMENTS_DATA);
                post.comments = getPostCommentsFromJSONArray(commentsJSONArray);
            }
        }
        return post;
    }

    private static ArrayList<IGPostComment> getPostCommentsFromJSONArray(JSONArray postCommentsJSON) {
        ArrayList<IGPostComment> postComments = new ArrayList<>();
        if (postCommentsJSON != null) {
            for (int i = 0; i < postCommentsJSON.length(); i++) {
                JSONObject postCommentJSON = postCommentsJSON.optJSONObject(i);
                IGPostComment postComment = getPostCommentFromJSON(postCommentJSON);
                if (postComment != null) {
                    postComments.add(postComment);
                }
            }
        }
        return postComments;
    }

    private static IGPostComment getPostCommentFromJSON(JSONObject postCommentJSON) {
        IGPostComment comment = new IGPostComment();
        if (postCommentJSON != null) {
            comment.createdTime = postCommentJSON.optLong(IGPostConstants.IG_POST_COMMENT_CREATED_TIME);
            comment.text = postCommentJSON.optString(IGPostConstants.IG_POST_COMMENT_TEXT);
            JSONObject userJSON = postCommentJSON.optJSONObject(IGPostConstants.IG_POST_COMMENT_AUTHOR);
            comment.author = getUserFromJSON(userJSON);
        }
        return comment;
    }

    private static IGUser getUserFromJSON(JSONObject userJSON) {
        IGUser user = new IGUser();
        if (userJSON != null) {
            user.username = userJSON.optString(IGPostConstants.IG_USER_USERNAME);
            user.fullName = userJSON.optString(IGPostConstants.IG_USER_FULL_NAME);
            user.profileImageUrl = userJSON.optString(IGPostConstants.IG_USER_IMAGE_URL);
        }
        return user;
    }
}

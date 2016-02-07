package com.codepath.kpu.pix.network;

import com.codepath.kpu.pix.models.IGPost;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kpu on 2/6/16.
 */
public class IGPostProvider {

    final private String BASE_URL = "https://api.instagram.com/v1/media/popular?client_id=";
    final private String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    private String FETCH_URL;

    private AsyncHttpClient client;

    public interface IGOnPostsFetched {
        void onSuccess(ArrayList<IGPost> fetchedPosts);
        void onFailure(String errorString);
    }

    public IGPostProvider() {
        client = new AsyncHttpClient();
        FETCH_URL = BASE_URL + CLIENT_ID;
    }

    public void fetchPopularPosts(final IGOnPostsFetched callback) {
        client.get(FETCH_URL, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<IGPost> fetchedPosts = IGPostResponseSerializer.handleFetchPopularPostsResponse(response);
                callback.onSuccess(fetchedPosts);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onFailure(responseString);
            }
        });
    }
}

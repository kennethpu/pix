package com.codepath.kpu.pix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codepath.kpu.pix.models.IGPost;
import com.codepath.kpu.pix.network.IGPostProvider;

import java.util.ArrayList;

public class IGMainActivity extends AppCompatActivity {

    private ArrayList<IGPost> posts;
    private IGPostsAdapter aPosts;

    private IGPostProvider postProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<>();

        // Create adapter and link it to the data source
        aPosts = new IGPostsAdapter(this, posts);
        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPosts);

        postProvider = new IGPostProvider();

        // Send out API request to Instagram Popular Photos endpoint
        refreshPopularPosts();
    }

    private void refreshPopularPosts() {
        postProvider.fetchPopularPosts(new IGPostProvider.IGOnPostsFetched() {
            @Override
            public void onSuccess(ArrayList<IGPost> fetchedPosts) {
                posts.clear();
                posts.addAll(fetchedPosts);
                aPosts.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorString) {
                // TODO: handle fetch failure
            }
        });
    }
}

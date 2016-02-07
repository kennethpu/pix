package com.codepath.kpu.pix;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codepath.kpu.pix.models.IGPost;
import com.codepath.kpu.pix.network.IGPostProvider;

import java.util.ArrayList;

public class IGMainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    private IGPostsAdapter postsAdapter;
    private IGPostProvider postProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up pull-to-refresh
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPopularPosts();
            }
        });

        // Create adapter and link it to the data source
        postsAdapter = new IGPostsAdapter(this, new ArrayList<IGPost>());
        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(postsAdapter);

        // Initialize our network adapter to make API requests to Instagram Popular Posts endpoint
        postProvider = new IGPostProvider();

        // Refresh UI
        refreshPopularPosts();
    }

    private void refreshPopularPosts() {
        postProvider.fetchPopularPosts(new IGPostProvider.IGOnPostsFetched() {
            @Override
            public void onSuccess(ArrayList<IGPost> fetchedPosts) {
                postsAdapter.clear();
                postsAdapter.addAll(fetchedPosts);

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorString) {
                // TODO: handle fetch failure

                swipeContainer.setRefreshing(false);
            }
        });
    }
}

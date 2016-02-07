package com.codepath.kpu.pix;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.kpu.pix.models.IGPost;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Created by kpu on 2/6/16.
 */
public class IGPostsAdapter extends ArrayAdapter<IGPost> {

    public IGPostsAdapter(Context context, List<IGPost> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IGPost post = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        ImageView rivProfilePicture = (ImageView) convertView.findViewById(R.id.rivProfilePicture);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);

        rivProfilePicture.setImageResource(0);
        Picasso.with(getContext()).load(post.author.profileImageUrl).placeholder(R.drawable.icon_profile).fit().into(rivProfilePicture);

        tvUserName.setText(post.author.username);

        tvTimeStamp.setText(getRelativeTimeStamp(post.createdTime));

        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(post.imageUrl).placeholder(R.drawable.icon_placeholder).into(ivPhoto);

        tvLikesCount.setText(String.format("%,d likes", post.likesCount));

        if (post.caption != null) {
            String captionText = String.format("<b><font color=#2d5b80>%s</font></b> %s", post.author.username, post.caption);
            tvCaption.setText(Html.fromHtml(captionText));
        }

        return convertView;
    }

    private String getRelativeTimeStamp(long timeStamp) {
        String relativeTimeString = getRelativeTimeSpanString(timeStamp * 1000).toString();
        String[] stringComponents = relativeTimeString.split(" ");

        return stringComponents[0] + stringComponents[1].charAt(0);
    }
}

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
import com.codepath.kpu.pix.models.IGPostComment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Created by kpu on 2/6/16.
 */
public class IGPostsAdapter extends ArrayAdapter<IGPost> {

    private static class IGViewHolder {
        ImageView rivProfilePicture;
        TextView tvUserName;
        TextView tvTimeStamp;
        ImageView ivPhoto;
        TextView tvLikesCount;
        TextView tvCaption;
        TextView tvComments;
    }

    public IGPostsAdapter(Context context, List<IGPost> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IGPost post = getItem(position);

        IGViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new IGViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder.rivProfilePicture = (ImageView) convertView.findViewById(R.id.rivProfilePicture);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IGViewHolder) convertView.getTag();
        }

        viewHolder.rivProfilePicture.setImageResource(0);
        Picasso.with(getContext()).load(post.author.profileImageUrl).placeholder(R.drawable.icon_profile).fit().into(viewHolder.rivProfilePicture);

        viewHolder.tvUserName.setText(post.author.username);

        viewHolder.tvTimeStamp.setText(getRelativeTimeStamp(post.createdTime));

        viewHolder.ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(post.imageUrl).placeholder(R.drawable.icon_placeholder).into(viewHolder.ivPhoto);

        viewHolder.tvLikesCount.setText(String.format("%,d likes", post.likesCount));

        if (post.caption != null) {
            String captionText = String.format("<b><font color=#2d5b80>%s</font></b> %s", post.author.username, post.caption);
            viewHolder.tvCaption.setText(Html.fromHtml(captionText));
        }

        ArrayList<IGPostComment> comments = post.comments;
        if (comments.size() >= 2) {
            IGPostComment comment1 = comments.get(comments.size()-2);
            IGPostComment comment2 = comments.get(comments.size()-1);
            String commentText1 = String.format("<b><font color=#2d5b80>%s</font></b> %s", comment1.author.username, comment1.text);
            String commentText2 = String.format("<b><font color=#2d5b80>%s</font></b> %s", comment2.author.username, comment2.text);
            viewHolder.tvComments.setText(Html.fromHtml(commentText1 + "<br>" + commentText2));
        } else if (comments.size() >= 1) {
            IGPostComment comment = comments.get(comments.size()-1);
            String commentText = String.format("<b><font color=#2d5b80>%s</font></b> %s", comment.author.username, comment.text);
            viewHolder.tvComments.setText(Html.fromHtml(commentText));
        }

        return convertView;
    }

    private String getRelativeTimeStamp(long timeStamp) {
        String relativeTimeString = getRelativeTimeSpanString(timeStamp * 1000).toString();
        String[] stringComponents = relativeTimeString.split(" ");

        return stringComponents[0] + stringComponents[1].charAt(0);
    }
}

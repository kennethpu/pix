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

/**
 * Created by kpu on 2/6/16.
 */
public class IGPostsAdapter extends ArrayAdapter<IGPost> {

    public IGPostsAdapter(Context context, List<IGPost> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IGPost photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);

        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.icon_placeholder).into(ivPhoto);
        tvLikesCount.setText(String.format("%,d likes", photo.likesCount));

        if (photo.caption != null) {
            String captionText = String.format("<b><font color=#2d5b80>%s</font></b> %s", photo.author.username, photo.caption);
            tvCaption.setText(Html.fromHtml(captionText));
        }

        return convertView;
    }
}

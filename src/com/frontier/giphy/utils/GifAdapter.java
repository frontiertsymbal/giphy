package com.frontier.giphy.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.frontier.giphy.R;

import java.util.List;

public class GifAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;

    public GifAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        Glide.with(convertView.getContext()).load(getItem(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        return convertView;
    }
}

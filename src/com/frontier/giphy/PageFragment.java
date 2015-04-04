package com.frontier.giphy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PageFragment extends Fragment {
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        Glide.with(getActivity()).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter().into(image);

        return view;
    }
}
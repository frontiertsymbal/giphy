package com.frontier.giphy.utils;

import android.net.Uri;

public class UrlStringBuilder {

    public static String createUrlString(String searchRequest) {

        int limit = 10;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(Const.HOST)
                .appendPath("v1")
                .appendPath("gifs")
                .appendPath("search")
                .appendQueryParameter("q", searchRequest)
                .appendQueryParameter("api_key", Const.API_KEY)
                .appendQueryParameter("limit", String.valueOf(limit));

        return builder.build().toString().replace("%20", "+");
    }
}

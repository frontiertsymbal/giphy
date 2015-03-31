package com.frontier.giphy.utils;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class GetRequestToJSonString {

    public static String getString(String urlString) {
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            DefaultHttpClient client = new DefaultHttpClient(params);
            HttpGet get = new HttpGet(urlString);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                Log.e(Const.LOG_TAG, "The server responded with " + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (Exception e) {
            Log.e(Const.LOG_TAG, "Error in doInBackground", e);
            return null;
        }
    }
}

package com.frontier.giphy;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.frontier.giphy.model.GiphyItem;
import com.frontier.giphy.model.GiphyResponse;
import com.frontier.giphy.utils.Const;
import com.frontier.giphy.utils.GetRequestToJSonString;
import com.frontier.giphy.utils.UrlStringBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private String url = null;
    private ListView viewResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EditText searchRequest = (EditText) findViewById(R.id.searchRequest);
        viewResult = (ListView) findViewById(R.id.viewResult);

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchRequest.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter a query please", Toast.LENGTH_LONG).show();
                } else {
                    if (!isOnline()) {
                        url = UrlStringBuilder.createUrlString(searchRequest.getText().toString());
                        searchRequest.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        Toast.makeText(MainActivity.this, "Searching", Toast.LENGTH_LONG).show();
                        new RequestTask().execute();
                    } else {
                        Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private class RequestTask extends AsyncTask<Void, Void, List<GiphyItem>> {

        @Override
        protected List<GiphyItem> doInBackground(Void... params) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(GetRequestToJSonString.getString(url), GiphyResponse.class).getList();
        }

        @Override
        protected void onPostExecute(List<GiphyItem> list) {
            if (list.size() != 0) {
                List<String> stringList = new ArrayList<>();

                for (GiphyItem aList : list) {
                    stringList.add(aList.getImages().getOriginal().getUrl());
                }

                viewResult.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringList));
                Toast.makeText(MainActivity.this, "Search finish", Toast.LENGTH_LONG).show();
            } else {
                Log.e(Const.LOG_TAG, "Error loading data");
                Toast.makeText(MainActivity.this, "Error loading data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() == null;
    }
}

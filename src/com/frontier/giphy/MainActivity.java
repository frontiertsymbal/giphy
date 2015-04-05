package com.frontier.giphy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.costum.android.widget.LoadMoreListView;
import com.frontier.giphy.model.GiphyItem;
import com.frontier.giphy.model.GiphyResponse;
import com.frontier.giphy.utils.Const;
import com.frontier.giphy.utils.GetRequestToJSonString;
import com.frontier.giphy.utils.GifAdapter;
import com.frontier.giphy.utils.UrlStringBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 1;
    private String url = null;
    private LoadMoreListView viewResult;
    private int limit = 10;
    private int offset = 0;
    private List<String> stringList = new ArrayList<>();
    private GifAdapter adapter;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EditText searchRequest = (EditText) findViewById(R.id.searchRequest);
        viewResult = (LoadMoreListView) findViewById(R.id.viewResult);
        imageView = (ImageView) findViewById(R.id.logo);

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchRequest.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter a query please", Toast.LENGTH_LONG).show();
                } else {
                    if (!isOnline()) {
                        offset = 0;
                        url = UrlStringBuilder.createUrlString(searchRequest.getText().toString().trim(), limit, offset);
                        searchRequest.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        Toast.makeText(MainActivity.this, "Searching", Toast.LENGTH_LONG).show();
                        new RequestTask().execute();
                    } else {
                        Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        viewResult.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            public void onLoadMore() {
                offset += 10;
                url = UrlStringBuilder.createUrlString(searchRequest.getText().toString(), limit, offset);
                new LoadMoreTask().execute();
            }
        });

        viewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PagerActivity.class);
                intent.putExtra("urls", (Serializable) stringList);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);
                showActivityAnimation(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int tmp = data.getIntExtra("position", 0);
        Log.i(Const.LOG_TAG, "PagerPosition " + tmp);
        viewResult.setSelection(tmp);
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
                stringList.clear();
                for (GiphyItem aList : list) {
                    stringList.add(aList.getImages().getOriginal().getUrl());
                }

                adapter = new GifAdapter(MainActivity.this, stringList);
                viewResult.setAdapter(adapter);
                imageView.setVisibility(View.INVISIBLE);
                
                Toast.makeText(MainActivity.this, "Search finish", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(Const.LOG_TAG, "Error loading data");
                Toast.makeText(MainActivity.this, "Error loading data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class LoadMoreTask extends AsyncTask<Void, Void, List<GiphyItem>> {

        @Override
        protected List<GiphyItem> doInBackground(Void... params) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(GetRequestToJSonString.getString(url), GiphyResponse.class).getList();
        }

        @Override
        protected void onPostExecute(List<GiphyItem> list) {
            if (list.size() != 0) {

                for (GiphyItem aList : list) {
                    stringList.add(aList.getImages().getOriginal().getUrl());
                }
                adapter.notifyDataSetChanged();
                viewResult.onLoadMoreComplete();
            } else {
                Log.e(Const.LOG_TAG, "Error loading data");
                Toast.makeText(MainActivity.this, "Error loading data", Toast.LENGTH_LONG).show();
            }
        }

        protected void onCancelled() {
            viewResult.onLoadMoreComplete();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() == null;
    }

    public static void showActivityAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}

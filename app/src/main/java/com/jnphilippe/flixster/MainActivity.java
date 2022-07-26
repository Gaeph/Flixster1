package com.jnphilippe.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.jnphilippe.flixster.Models.Movie;
import com.jnphilippe.flixster.adapter.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String Now_Playing_Url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies =findViewById(R.id.rvMovies);
        movies =new ArrayList<>();

       // Create the adapter
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        // Set the adapter on the Recycle View
        rvMovies.setAdapter(movieAdapter);
        //Set a Layout Manager on the recycle view
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Now_Playing_Url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.d(TAG,  "Onsuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies: " + movies.size());
                } catch (JSONException e) {
                      Log.e(TAG,"Hit json exception", e);

                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,  "OnFailure");
            }
        });





    }
}
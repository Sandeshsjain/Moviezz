package com.example.moviemafia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemafia.Adapter.TrendingMovieListAdapter;
import com.example.moviemafia.Bean.TrendingMovieBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.Network.Myutlis;
import com.example.moviemafia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrendingMovieListActivity extends AppCompatActivity {
    RecyclerView recyclerViewTrendingMovieList;
    RequestQueue requestQueue;
    List<TrendingMovieBean> trendingMovieBeanList;
    TrendingMovieListAdapter trendingMovieListAdapter;
    public int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_movie_list);
        recyclerViewTrendingMovieList = findViewById(R.id.recyclerViewTrendingMovieList);
        requestQueue = Volley.newRequestQueue(this);
        trendingMovieBeanList = new ArrayList<>();
        getData(count);
        trendingMovieListAdapter = new TrendingMovieListAdapter(this,trendingMovieBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewTrendingMovieList.setLayoutManager(linearLayoutManager);
        recyclerViewTrendingMovieList.setAdapter(trendingMovieListAdapter);
        recyclerViewTrendingMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!(Myutlis.isLastItemDisplaying(recyclerViewTrendingMovieList))){
                    count++;
                    Log.d("helllo", "onScrolled: " + count);
                    getData(count);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void getData(int pageCount) {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.trending + "movie/week?page="+pageCount+"&api_key="+ Constant.key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject rootObject = new JSONObject(response);
                    JSONArray mainArray = rootObject.getJSONArray("results");
                    Log.d("Activity", "TrendingMoviewee: "+mainArray);
                    for(int i = 0;i<mainArray.length();i++){
                        JSONObject data = mainArray.getJSONObject(i);
                        TrendingMovieBean bean = new TrendingMovieBean();
                        bean.setAdult(data.getString("adult"));
                        bean.setBackdrop_path(data.getString("backdrop_path"));
                        bean.setId(data.getString("id"));
                        bean.setMedia_type(data.getString("media_type"));
                        bean.setOriginal_language(data.getString("original_language"));
                        bean.setOriginal_title(data.getString("original_title"));
                        bean.setTitle(data.getString("title"));
                        bean.setOverview(data.getString("overview"));
                        bean.setPopularity(data.getString("popularity"));
                        bean.setPoster_path(data.getString("poster_path"));
                        bean.setVideo(data.getString("video"));
                        bean.setRelease_date(data.getString("release_date"));
                        bean.setVote_average(data.getString("vote_average"));
                        bean.setVote_count(data.getString("vote_count"));
                        trendingMovieBeanList.add(bean);
                    }
                    trendingMovieListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Activity", "onErrorResponse: "+error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
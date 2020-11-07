package com.example.moviemafia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemafia.Adapter.TrendingShowListAdapter;
import com.example.moviemafia.Bean.TrendingShowBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.Network.Myutlis;
import com.example.moviemafia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrendingShowListActivity extends AppCompatActivity {
    RecyclerView recyclertrendingshowlist;
    public int count = 1;
    RequestQueue requestQueue;
    List<TrendingShowBean> trendingShowBeanList;
    TrendingShowListAdapter trendingShowListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_show_list);
        recyclertrendingshowlist = findViewById(R.id.recyclertrendingshowlist);
        trendingShowBeanList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        getData(count);
        trendingShowListAdapter = new TrendingShowListAdapter(this,trendingShowBeanList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclertrendingshowlist.setLayoutManager(linearLayoutManager);
        recyclertrendingshowlist.setAdapter(trendingShowListAdapter);

       recyclertrendingshowlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               if(!(Myutlis.isLastItemDisplaying(recyclertrendingshowlist))){
                   count++;
                   Log.d("helllo", "onScrolled: " + count);
                   getData(count);
               }
               super.onScrollStateChanged(recyclerView, newState);
           }
       });
    }

    private void getData(int pageCount) {

        StringRequest request = new StringRequest(Request.Method.GET, Constant.trending + "tv/week?page="+pageCount+"&api_key="+ Constant.key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mainArray = jsonObject.getJSONArray("results");
                    Log.d("Activity", "TrendingShowwee: "+mainArray);
                    for(int i = 0;i<mainArray.length();i++){
                        JSONObject data = mainArray.getJSONObject(i);
                        TrendingShowBean bean = new TrendingShowBean();
                        bean.setId(data.getString("id"));
                        bean.setBackdrop_path(data.getString("backdrop_path"));
                        bean.setName(data.getString("name"));
                        bean.setOriginal_name(data.getString("original_name"));
                        bean.setFirst_air_date(data.getString("first_air_date"));
                        bean.setMedia_type(data.getString("media_type"));
                        bean.setPopularity(data.getString("popularity"));
                        bean.setOriginal_language(data.getString("original_language"));
                        bean.setOverview(data.getString("overview"));
                        bean.setOriginal_language(data.getString("original_language"));
                        bean.setPoster_path(data.getString("poster_path"));
                        bean.setVote_average(data.getString("vote_average"));
                        bean.setVote_count(data.getString("vote_count"));

                        trendingShowBeanList.add(bean);
                    }
                    trendingShowListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Activity", "onErrorResponseShow: "+error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

}
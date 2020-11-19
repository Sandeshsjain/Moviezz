package com.example.moviemafia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemafia.Adapter.TrendingPersonListAdpater;
import com.example.moviemafia.Bean.TrendingPersonBean;
import com.example.moviemafia.Bean.TrendingPersonKnowForBean;
import com.example.moviemafia.Fragment.HomeFragment;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.Network.Myutlis;
import com.example.moviemafia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrendingPersonListActivity extends AppCompatActivity {
    TrendingPersonListAdpater trendingPersonListAdpater;
    RecyclerView recylerViewTrendingPersonList;
    RequestQueue requestQueue;
    List<TrendingPersonBean> trendingPersonBeanList;
    public int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_person_list);
        recylerViewTrendingPersonList = findViewById(R.id.recylerViewTrendingPersonList);
        trendingPersonBeanList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        getData(count);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerViewTrendingPersonList.setLayoutManager(linearLayoutManager);
        trendingPersonListAdpater = new TrendingPersonListAdpater(this,trendingPersonBeanList);
        recylerViewTrendingPersonList.setAdapter(trendingPersonListAdpater);
            recylerViewTrendingPersonList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if(!(Myutlis.isLastItemDisplaying(recylerViewTrendingPersonList))){
                        count++;
                        Log.d("helllo", "onScrolled: " + count);
                        getData(count);
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
    }
    private void getData(int pageCount) {
        Log.d("MyApplication", "getData:");
        StringRequest request = new StringRequest(Request.Method.GET, Constant.trending+"person/week?page="+pageCount+"&api_key="+Constant.key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject rootObject;
                try {
                    rootObject = new JSONObject(response);
                    JSONArray mainArray = rootObject.getJSONArray("results");
                    Log.d("List", "getData: "+mainArray);
                    for(int i=0;i<mainArray.length();i++){
                        JSONObject jsonObject = mainArray.getJSONObject(i);
                        TrendingPersonBean trendingPersonBean = new TrendingPersonBean();
                        trendingPersonBean.setName(jsonObject.getString("name"));
                        trendingPersonBean.setProfile_path(jsonObject.getString("profile_path"));
                        trendingPersonBean.setAdult(jsonObject.getString("adult"));
                        trendingPersonBean.setGender(jsonObject.getString("gender"));
                        trendingPersonBean.setId(jsonObject.getString("id"));
                        trendingPersonBean.setKnown_for_department(jsonObject.getString("known_for_department"));
                        trendingPersonBean.setPopularity(jsonObject.getString("popularity"));
                        trendingPersonBean.setMedia_type(jsonObject.getString("media_type"));
                        JSONArray knownFor = jsonObject.getJSONArray("known_for");
                        List<TrendingPersonKnowForBean> trendingPersonKnowForBeanList = new ArrayList<>();
                        for(int j = 0;j<knownFor.length();j++){
                            JSONObject knownForObject = knownFor.getJSONObject(j);
                            TrendingPersonKnowForBean trendingPersonKnowForBean = new TrendingPersonKnowForBean();
                            trendingPersonKnowForBean.setAdult(knownForObject.getString("adult"));
                            trendingPersonKnowForBean.setBackdrop_path(knownForObject.getString("backdrop_path"));
                            trendingPersonKnowForBean.setId(knownForObject.getString("id"));
                            trendingPersonKnowForBean.setMedia_type(knownForObject.getString("media_type"));
                            trendingPersonKnowForBean.setOriginal_language(knownForObject.getString("original_title"));
                            trendingPersonKnowForBean.setOriginal_title(knownForObject.getString("adult"));
                            trendingPersonKnowForBean.setOverview(knownForObject.getString("overview"));
                            trendingPersonKnowForBean.setPoster_path(knownForObject.getString("poster_path"));
                            trendingPersonKnowForBean.setRelease_date(knownForObject.getString("release_date"));
                            trendingPersonKnowForBean.setTitle(knownForObject.getString("title"));
                            trendingPersonKnowForBean.setVote_average(knownForObject.getString("vote_average"));
                            trendingPersonKnowForBean.setVote_count(knownForObject.getString("vote_count"));
                            trendingPersonKnowForBeanList.add(trendingPersonKnowForBean);
                        }
                        trendingPersonBean.setTrendingPersonKnowForBeanList(trendingPersonKnowForBeanList);
                        trendingPersonBeanList.add(trendingPersonBean);
                    }
                    trendingPersonListAdpater.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OnActivity", "onErrorResponse: "+error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
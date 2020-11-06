package com.example.moviemafia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemafia.Activity.HomeActivity;
import com.example.moviemafia.Activity.MainActivity;
import com.example.moviemafia.Activity.TrendingPersonListActivity;
import com.example.moviemafia.Adapter.TrendingPersonAdapter;
import com.example.moviemafia.Bean.TrendingPersonBean;
import com.example.moviemafia.Bean.TrendingPersonKnowForBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    List<TrendingPersonBean> trendingPersonList;
    RequestQueue requestQueue;
    RecyclerView recycler_view_trending_person;
    TrendingPersonAdapter trendingPersonAdapter;
    TextView trending_personmore;
    public static final String TAG = "HomeFragment";
    public HomeFragment() {
        //Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        trendingPersonList = new ArrayList<>();
        trending_personmore = view.findViewById(R.id.trending_personmore);
        requestQueue = Volley.newRequestQueue(getContext());
        getTrendingPerson();
        recycler_view_trending_person = view.findViewById(R.id.recycler_view_trending_person);
        recycler_view_trending_person.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingPersonAdapter = new TrendingPersonAdapter(getContext(),trendingPersonList);
        recycler_view_trending_person.setAdapter(trendingPersonAdapter);
        trending_personmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrendingPersonListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void getTrendingPerson() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.trendingPerson+"person/day?api_key="+Constant.key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject rootObject;
                try {
                    rootObject = new JSONObject(response);
                    JSONArray mainArray = rootObject.getJSONArray("results");
                    Log.d(TAG, "onResponse: "+mainArray);
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
                        trendingPersonList.add(trendingPersonBean);
                    }
                    trendingPersonAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
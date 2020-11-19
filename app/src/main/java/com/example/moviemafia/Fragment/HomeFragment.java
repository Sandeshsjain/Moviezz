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
import com.example.moviemafia.Activity.TrendingMovieListActivity;
import com.example.moviemafia.Activity.TrendingPersonListActivity;
import com.example.moviemafia.Activity.TrendingShowListActivity;
import com.example.moviemafia.Adapter.ImageSliderAdapter;
import com.example.moviemafia.Adapter.MovieAndShowAdapter;
import com.example.moviemafia.Adapter.PopularMovieAdapter;
import com.example.moviemafia.Adapter.TrendingPersonAdapter;
import com.example.moviemafia.Adapter.TrendingShowAdapter;
import com.example.moviemafia.Bean.PopulerMovieBean;
import com.example.moviemafia.Bean.TrendingMovieBean;
import com.example.moviemafia.Bean.TrendingPersonBean;
import com.example.moviemafia.Bean.TrendingPersonKnowForBean;
import com.example.moviemafia.Bean.TrendingShowBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    //all list
    List<TrendingPersonBean> trendingPersonList;
    List<TrendingMovieBean> trendingMovieBeanList;
    List<TrendingShowBean> trendingShowBeanList;
    List<PopulerMovieBean> populerMovieBeanList;
    //request queue
    RequestQueue requestQueue;

    //recyclerView
    RecyclerView recycler_view_trending_person;
    RecyclerView recycler_view_trending_movie;
    RecyclerView recycler_view_trending_tvshow;
    RecyclerView recycler_view_popular_movie;
    SliderView sliderView;


    //adapter
    TrendingPersonAdapter trendingPersonAdapter;
    MovieAndShowAdapter movieAndShowAdapter;
    TrendingShowAdapter trendingShowAdapter;
    ImageSliderAdapter imageSliderAdapter;
    PopularMovieAdapter popularMovieAdapter;
    //textView
    TextView trending_personmore,trendingmoviemore,trendingtvshowmore;

    public static final String TAG = "HomeFragment";
    public HomeFragment() {
        //Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        //more button
        trending_personmore = view.findViewById(R.id.trending_personmore);
        trendingmoviemore = view.findViewById(R.id.trendingmoviemore);
        trendingtvshowmore = view.findViewById(R.id.trendingtvshowmore);

        trendingPersonList = new ArrayList<>();
        trendingMovieBeanList = new ArrayList<>();
        trendingShowBeanList = new ArrayList<>();
        populerMovieBeanList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

        recycler_view_trending_person = view.findViewById(R.id.recycler_view_trending_person);
        recycler_view_trending_movie = view.findViewById(R.id.recycler_view_trending_movie);
        recycler_view_trending_tvshow = view.findViewById(R.id.recycler_view_trending_tvshow);
        recycler_view_popular_movie = view.findViewById(R.id.recycler_view_popular_movie);
        sliderView = view.findViewById(R.id.imageSlider);

        recycler_view_trending_tvshow.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler_view_trending_movie.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler_view_trending_person.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view_popular_movie.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getTrendingPerson();
        getTrendingMovie();
        getTrendingShow();
        getPopularMovie();

        trendingPersonAdapter = new TrendingPersonAdapter(getContext(),trendingPersonList);
        movieAndShowAdapter = new MovieAndShowAdapter(getContext(),trendingMovieBeanList);
        trendingShowAdapter = new TrendingShowAdapter(getContext(),trendingShowBeanList);
        imageSliderAdapter = new ImageSliderAdapter(getContext(),trendingMovieBeanList);
        popularMovieAdapter = new PopularMovieAdapter(getContext(),populerMovieBeanList);

        recycler_view_trending_movie.setAdapter(movieAndShowAdapter);
        recycler_view_trending_person.setAdapter(trendingPersonAdapter);
        recycler_view_trending_tvshow.setAdapter(trendingShowAdapter);
        recycler_view_popular_movie.setAdapter(popularMovieAdapter);
        sliderView.setSliderAdapter(imageSliderAdapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

        trending_personmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrendingPersonListActivity.class);
                startActivity(intent);
            }
        });
        trendingmoviemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrendingMovieListActivity.class);
                startActivity(intent);
            }
        });

        trendingtvshowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrendingShowListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getPopularMovie() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.popularMovie + 1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mainArray = jsonObject.getJSONArray("results");
                    Log.d(TAG, "poplar mavie: "+mainArray);
                    for(int i=0;i<mainArray.length();i++){
                        JSONObject data = mainArray.getJSONObject(i);
                        PopulerMovieBean populerMovieBean = new PopulerMovieBean();
                        populerMovieBean.setPopularity(data.getString("popularity"));
                        populerMovieBean.setVote_count(data.getString("vote_count"));
                        populerMovieBean.setVideo(data.getString("video"));
                        populerMovieBean.setPoster_path(data.getString("poster_path"));
                        populerMovieBean.setId(data.getString("id"));
                        populerMovieBean.setAdult(data.getString("adult"));
                        populerMovieBean.setBackdrop_path(data.getString("backdrop_path"));
                        populerMovieBean.setOriginal_language(data.getString("original_language"));
                        populerMovieBean.setOriginal_title(data.getString("original_title"));
                        populerMovieBean.setTitle(data.getString("title"));
                        populerMovieBean.setVote_average(data.getString("vote_average"));
                        populerMovieBean.setOverview(data.getString("overview"));
                        populerMovieBean.setRelease_date(data.getString("release_date"));
                        populerMovieBeanList.add(populerMovieBean);
                    }
                    popularMovieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponseShow: "+error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    private void getTrendingShow() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.trending + "tv/week?api_key=" + Constant.key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mainArray = jsonObject.getJSONArray("results");
                    Log.d(TAG, "TrendingShowwee: "+mainArray);
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
                    trendingShowAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponseShow: "+error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void getTrendingMovie() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.trending + "movie/week?api_key=" + Constant.key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject rootObject = new JSONObject(response);
                    JSONArray mainArray = rootObject.getJSONArray("results");
                    Log.d(TAG, "TrendingMoviewee: "+mainArray);
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
                    trendingPersonAdapter.notifyDataSetChanged();
                    imageSliderAdapter.notifyDataSetChanged();
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

    private void getTrendingPerson() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.trending+"person/week?api_key="+Constant.key, new Response.Listener<String>() {
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
                            trendingPersonKnowForBean.setRelease_date(knownForObject.getString(                                                                                                                                                                                                                                                                                                                                                                                            "release_date"));
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
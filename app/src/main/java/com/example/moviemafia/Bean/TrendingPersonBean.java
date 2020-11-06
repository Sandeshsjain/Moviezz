package com.example.moviemafia.Bean;

import java.util.List;

public class TrendingPersonBean {
    String name,profile_path;
    String adult ,gender,id,known_for_department,popularity,media_type;

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    List<TrendingPersonKnowForBean> trendingPersonKnowForBeanList;

    public List<TrendingPersonKnowForBean> getTrendingPersonKnowForBeanList() {
        return trendingPersonKnowForBeanList;
    }

    public void setTrendingPersonKnowForBeanList(List<TrendingPersonKnowForBean> trendingPersonKnowForBeanList) {
        this.trendingPersonKnowForBeanList = trendingPersonKnowForBeanList;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrendingPersonBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}

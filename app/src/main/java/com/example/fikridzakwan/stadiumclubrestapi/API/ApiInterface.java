package com.example.fikridzakwan.stadiumclubrestapi.API;

import com.example.fikridzakwan.stadiumclubrestapi.Model.StadiumResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/v1/json/1/search_all_teams.php")
    Call<StadiumResponse> getStadium(@Query("s") String Soccer, @Query("c") String England);

    @GET("/api/v1/json/1/lookupteam.php")
    Call<StadiumResponse> getDetailStadium(@Query("id") int id);
}

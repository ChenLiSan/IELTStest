package com.example.lysanchen.ieltstest.restservice;

import com.example.lysanchen.ieltstest.restmodels.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Lysan Chen on 6/12/2018.
 */

public interface UserService {

    @GET("/api/sessions")
    Call<List<Session>> getSessions();
}

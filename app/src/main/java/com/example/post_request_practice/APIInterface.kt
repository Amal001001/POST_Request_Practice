package com.example.post_request_practice

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("/test/")
    fun doGetUsersList(): Call<List<Users.UserDetails?>>?
//@Body userdetails: UserDetails

    @POST("/test/")
    fun addUser(@Body userdetails: Users.UserDetails): Call<Users.UserDetails?>?
}
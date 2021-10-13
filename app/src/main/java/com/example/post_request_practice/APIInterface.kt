package com.example.post_request_practice

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/test/")
    fun doGetUsersList(): Call<ArrayList<user>>
//@Body userdetails: UserDetails

    @POST("/test/")
    fun addUser(@Body userdetails: user): Call<user>

    @GET("/test/{id}")
    fun getuser(@Path("id") id: Int): Call<user>

    @PUT("/test/{id}")
    fun updateUser(@Path("id") id: Int, @Body userdetails: user): Call<user>

    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}
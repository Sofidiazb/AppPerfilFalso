package com.example.perfilfalso

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getRandomUser(@Url url:String) : Response<UserResponse>
}




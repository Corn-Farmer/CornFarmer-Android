package com.example

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofit(): Retrofit{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.34.223.58:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}
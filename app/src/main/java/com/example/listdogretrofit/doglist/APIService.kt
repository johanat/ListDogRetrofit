package com.example.listdogretrofit.doglist


import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Response


interface APIService {
    // recibe  la parte restante de la base
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>
}
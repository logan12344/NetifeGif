package com.example.netifegif.data.api


import com.example.netifegif.BuildConfig
import com.example.netifegif.data.models.GifModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsApi {

    @GET("trending?")
    suspend fun fetchTrendingGifs(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("limit") limit: Int = 200,
        @Query("offset") offset: Int = 200,
        @Query("rating") rating: String = "g",
    ): GifModel

    @GET("search?")
    suspend fun fetchSearchGifs(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int = 200,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en",
    ) : GifModel

}
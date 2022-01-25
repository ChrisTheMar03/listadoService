package com.christhemar.carritorest.interfaces

import com.christhemar.carritorest.model.Articulo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ArticuloService {
    @GET("posts")
    fun articleList():Call<List<Articulo>>
}

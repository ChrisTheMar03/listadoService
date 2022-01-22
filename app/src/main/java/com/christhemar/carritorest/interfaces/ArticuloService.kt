package com.christhemar.carritorest.interfaces

import com.christhemar.carritorest.model.Articulo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val URL:String="http://192.168.1.42:3000/"

val retrofit= Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

interface ArticuloService {
    @GET("posts")
    fun articleList():Call<List<Articulo>>
}

object api{
    val service:ArticuloService by lazy{ retrofit.create(ArticuloService::class.java)}
}
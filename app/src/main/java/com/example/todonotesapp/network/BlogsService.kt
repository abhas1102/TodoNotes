package com.example.todonotesapp.network

import com.example.todonotesapp.model.Blog
import com.example.todonotesapp.model.Data
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://www.mocky.io/v2/5926ce9d11000096006ccb30/"
//5926ce9d11000096006ccb30
interface BlogsInterface{

    @GET("https://www.mocky.io/v2/5926ce9d11000096006ccb30/")
    suspend fun getBlogs():Response<Blog>

}
object BlogsService{
    val blogInstance : BlogsInterface
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
        blogInstance = retrofit.create(BlogsInterface::class.java)
    }
}
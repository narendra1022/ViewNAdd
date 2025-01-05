package com.example.swipe.data.network

import com.example.swipe.data.model.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("get")
    suspend fun getProducts(): List<Product>
}
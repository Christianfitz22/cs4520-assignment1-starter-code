package com.cs4520.assignment1

import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {
    @GET(Api.ENDPOINT)
    fun getProductList(): Call<ProductResponseModel>
}
package com.cs4520.assignment1

class ProductRepository {
    private val retrofit = RetrofitClient.getRetrofitInstance().create(ApiEndPoint::class.java)

    fun getAllProducts() = retrofit.getProductList()
}
package com.cs4520.assignment1

import com.google.gson.annotations.SerializedName

data class ProductResponseModel(val items: ArrayList<ProductData>)

data class ProductData(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @SerializedName("price")
    val price: Int)
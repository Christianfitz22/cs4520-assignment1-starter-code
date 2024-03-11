package com.cs4520.assignment1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _productResponseData = MutableLiveData<ProductResponseModel?>()
    val productResponseData : LiveData<ProductResponseModel?> = _productResponseData

    var productAdaptor: ProductAdaptor = ProductAdaptor()

    init {
        makeApiCall()
    }

    fun getAdaptor(): ProductAdaptor {
        return productAdaptor
    }

    fun setAdaptorData(data: ArrayList<ProductData>) {
        productAdaptor.setData(data)
        productAdaptor.notifyDataSetChanged()
    }

    fun makeApiCall() {
        val myData = RetrofitClient.getRetrofitInstance().create(ApiEndPoint::class.java)

        repository.getAllProducts().enqueue(object : retrofit2.Callback<ProductResponseModel> {
            override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {
                _productResponseData.value = null
                println("Attempted to get all products but api call failed")
            }

            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) {
                if (!response.isSuccessful()) _productResponseData.value =
                    null else _productResponseData.value = response.body()
            }
        })
    }
}
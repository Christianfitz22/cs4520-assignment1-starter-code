package com.cs4520.assignment1

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

class ProductViewModel() : ViewModel() {
    private val _productResponseData = MutableLiveData<ArrayList<ProductData>?>()
    // rather than ProductResponseModel, this needs to be an ArrayList of ProductData
    val productResponseData : LiveData<ArrayList<ProductData>?> = _productResponseData
    private val repository = ProductRepository()

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
        println("api call function begins")

        val service = RetrofitClient.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getProductList()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful()) {
                        println("response is successful")
                        _productResponseData.value = response.body()
                    } else {
                        _productResponseData.value = null
                        println("Attempted to get all products but api call failed")
                    }
                } catch (e: HttpException) {
                    println("HTTP exception found")
                } catch (e: Throwable) {
                    println("Unknown exception found")
                }
            }
        }

        /*
        val myData = RetrofitClient.getRetrofitInstance().create(ApiEndPoint::class.java)

        repository.getAllProducts().enqueue(object : retrofit2.Callback<ArrayList<ProductData>> {
            override fun onFailure(call: Call<ArrayList<ProductData>>, t: Throwable) {
                _productResponseData.value = null
                println("Attempted to get all products but api call failed")
            }

            override fun onResponse(
                call: Call<ArrayList<ProductData>>,
                response: Response<ArrayList<ProductData>>
            ) {
                println("on Response function begins")
                if (!response.isSuccessful()) _productResponseData.value =
                    null else _productResponseData.value = response.body()
            }
        })
         */
    }
}
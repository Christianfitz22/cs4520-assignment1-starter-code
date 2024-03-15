package com.cs4520.assignment1

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class ProductViewModel() : ViewModel() {
    private val _productResponseData = MutableLiveData<ArrayList<ProductData>?>()
    // rather than ProductResponseModel, this needs to be an ArrayList of ProductData
    val productResponseData : LiveData<ArrayList<ProductData>?> = _productResponseData
    private val repository = ProductRepository()

    var productAdaptor: ProductAdaptor = ProductAdaptor()

    private lateinit var database: ProductDatabase



    init {
        // database must be set here
        database = DatabaseHolder.database
        makeApiCall()
    }

    fun getAdaptor(): ProductAdaptor {
        return productAdaptor
    }

    fun setAdaptorData(data: ArrayList<ProductData>) {
        productAdaptor.setData(data)
    }

    fun makeApiCall() {
        println("api call function begins")

        val productDao = database.productDao()

        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable -> throwable.printStackTrace()}

        val service = RetrofitClient.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            val response: Response<ArrayList<ProductData>>

            try {
                response = service.getProductList()
            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main) {
                    // if database is full, populate with database instead
                    // else, update with null value
                    println("network failure recognized")
                    val productDataEntries = productDao.getAll()

                    if (productDataEntries.isEmpty()) {
                        println("product data entries empty")
                        _productResponseData.value = null
                    } else {
                        println("product data entries found")
                        val productData = ArrayList<ProductData>()

                        for (item in productDataEntries) {
                            productData.add(ProductData(item.name, item.type, item.expiryDate, item.price))
                        }

                        _productResponseData.value = productData
                    }
                }
                return@launch
            }


            println("response gotten, is " + response.isSuccessful)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        println("response is successful")
                        // update database with gotten response
                        _productResponseData.value = response.body()

                        val productDataEntries = ArrayList<ProductDBEntry>()

                        for (item in response.body()!!) {
                            productDataEntries.add(ProductDBEntry(0, item.name, item.type, item.expiryDate, item.price))
                        }

                        //productDao.deleteAll()
                        productDao.insertAll(productDataEntries)

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
    }
}
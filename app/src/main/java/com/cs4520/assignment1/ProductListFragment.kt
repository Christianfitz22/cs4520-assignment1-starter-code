package com.cs4520.assignment1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.databinding.ProductListFragmentBinding

class ProductListFragment : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView: RecyclerView = binding.productRecyclerView
        recyclerView.adapter = ProductAdaptor()

        productViewModel = ViewModelProvider(this, ProductViewModelFactory())[ProductViewModel::class.java]

        binding.productRecyclerView.adapter = productViewModel.getAdaptor()

        initObserver()

        return view
    }

    private fun initObserver() {
        println("init Observer function began")
        productViewModel.productResponseData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                val itFiltered = filterData(it)
                productViewModel.setAdaptorData(itFiltered)
                println("items gotten: " + itFiltered.size)
                if (itFiltered.size == 0) {
                    binding.noProductText.visibility = View.VISIBLE
                }
            } else {
                //Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                binding.productErrorText.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                println("error fetching data")
            }
        })
    }

    private fun filterData(data: ArrayList<ProductData>): ArrayList<ProductData> {
        val result = ArrayList<ProductData>()
        val dataIterator = data.iterator()

        println("data length: " + data.size)

        while (dataIterator.hasNext()) {
            val nextProduct = dataIterator.next()
            if (result.contains(nextProduct)) {
                println(nextProduct.name + " skipped")
            } else {
                println(nextProduct.name + " seen")
                result.add(nextProduct)
            }
        }

        println("result length: " + result.size)

        return result
    }
}
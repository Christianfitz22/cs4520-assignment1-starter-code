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
        // it here should be arraylist of product data rather than object
        productViewModel.productResponseData.observe(viewLifecycleOwner, Observer {
            // rather than it here, we can use viewModel.ResponseData.value
            if (it != null) {
                // progressbar.visibility = gone
                // binding.textView.visbility = hone
                // viewmodel setadapterdata to viewModel.ResponseData.value
                productViewModel.setAdaptorData(it)
                println("items gotten: " + it.size)
            } else {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                println("error fetching data")
            }
        })
    }
}
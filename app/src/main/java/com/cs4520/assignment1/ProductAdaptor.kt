package com.cs4520.assignment1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.ProductItemBinding

enum class ProductType {
    FOOD, EQUIPMENT
}

data class ProductEntry(val name: String, val type: ProductType, val date: String?, val price: Int)

class ProductAdaptor : RecyclerView.Adapter<ProductAdaptor.ProductViewHolder>() {
    var items = ArrayList<ProductData>()

    fun setData(data: ArrayList<ProductData>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(layoutInflater)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        println("On Bind View Holder began")
        if (position >= items.size) {
            return
        }
        val nextProduct = items[position]
        val nextType: ProductType
        if (nextProduct.type == "Food") {
            nextType = ProductType.FOOD
        } else {
            nextType = ProductType.EQUIPMENT
        }
        val nextDate: String?
        if (nextProduct.expiryDate == null) {
            nextDate = null
        } else {
            nextDate = nextProduct.expiryDate
        }
        val nextEntry = ProductEntry(nextProduct.name, nextType, nextDate, nextProduct.price)
        holder.bind(nextEntry)
    }

    override fun getItemCount(): Int {
        return productsDataset.size
    }

    inner class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val productNameView: TextView = binding.nameText
        private val productDateView: TextView = binding.dateText
        private val productPriceView: TextView = binding.priceText
        private val productFrame: View = binding.productFrame
        private val productImage: ImageView = binding.productImage

        fun bind(entry: ProductEntry) {
            productNameView.text = entry.name
            if (entry.date == null) {
                productDateView.visibility = View.GONE;
            } else {
                productDateView.text = entry.date
            }
            productPriceView.text = entry.price.toString()
            if (entry.type == ProductType.FOOD) {
                productFrame.setBackgroundColor(Color.parseColor("#FFD965"))
                productImage.setImageResource(R.drawable.food)
            } else {
                productFrame.setBackgroundColor(Color.parseColor("#E06666"))
                productImage.setImageResource(R.drawable.equipment)
            }
        }
    }
}
package com.cs4520.assignment1

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity

enum class ProductType {
    FOOD, EQUIPMENT
}

data class ProductEntry(val name: String, val type: ProductType, val date: String?, val price: Int)

class ProductAdaptor : RecyclerView.Adapter<ProductAdaptor.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val parameterList = productsDataset[position]
        val nextType: ProductType
        if ((parameterList[1] as String) == "Food") {
            nextType = ProductType.FOOD
        } else {
            nextType = ProductType.EQUIPMENT
        }
        val nextDate: String?
        if (parameterList[2] == null) {
            nextDate = null
        } else {
            nextDate = parameterList[2] as String
        }
        val nextEntry = ProductEntry(parameterList[0] as String, nextType, nextDate, parameterList[3] as Int)
        holder.bind(nextEntry)
    }

    override fun getItemCount(): Int {
        return productsDataset.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameView: TextView = itemView.findViewById(R.id.nameText)
        private val productDateView: TextView = itemView.findViewById(R.id.dateText)
        private val productPriceView: TextView = itemView.findViewById(R.id.priceText)
        private val productFrame: View = itemView.findViewById(R.id.productFrame)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)

        fun bind(entry: ProductEntry) {
            productNameView.text = entry.name
            productDateView.text = entry.date
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
package com.example.liststart.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.model.AndroidDispatchDTO // 데이터 모델

// RecyclerView Adapter for displaying dispatch items
class DispatchAdapter(private var dispatchList: List<AndroidDispatchDTO>) : RecyclerView.Adapter<DispatchAdapter.DispatchViewHolder>() {

    // ViewHolder class to hold references to each item view's components
    inner class DispatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.warehouseItemName) // Product name TextView
        val productQuantity: TextView = itemView.findViewById(R.id.warehouseItemDescription) // Product quantity TextView
    }

    // Create ViewHolder by inflating item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_warehouse, parent, false)
        return DispatchViewHolder(view)
    }

    // Bind data to each item view
    override fun onBindViewHolder(holder: DispatchViewHolder, position: Int) {
        val item = dispatchList[position]
        holder.productName.text = item.productNm // Set product name
        holder.productQuantity.text = "${item.orderDQty}개" // Set product quantity with "개" suffix
    }

    // Return total item count
    override fun getItemCount(): Int = dispatchList.size

    // Method to update data and refresh RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<AndroidDispatchDTO>) {
        dispatchList = newList
        notifyDataSetChanged()
        Log.d("myLog", "Adapter data updated with ${newList.size} items")
    }
}

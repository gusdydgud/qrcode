package com.example.liststart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.model.AndroidDispatchDTO

class DispatchAdapter(
    private var dispatchList: List<AndroidDispatchDTO>
) : RecyclerView.Adapter<DispatchAdapter.DispatchViewHolder>() {

    // 데이터 갱신을 위한 메서드 추가
    fun updateData(newDispatchList: List<AndroidDispatchDTO>) {
        dispatchList = newDispatchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_warehouse, parent, false)
        return DispatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: DispatchViewHolder, position: Int) {
        val dispatch = dispatchList[position]
        holder.productNameTextView.text = dispatch.productNm ?: "Unknown"
        holder.orderQuantityTextView.text = "${dispatch.orderDQty ?: 0}개"
    }

    override fun getItemCount() = dispatchList.size

    inner class DispatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productNameTextView: TextView = view.findViewById(R.id.warehouseItemName)
        val orderQuantityTextView: TextView = view.findViewById(R.id.warehouseItemDescription)

    }
}

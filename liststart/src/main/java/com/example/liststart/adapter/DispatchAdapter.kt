package com.example.liststart.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.model.AndroidDispatchDTO

// RecyclerView 어댑터: 출고 목록 표시
class DispatchAdapter(private var dispatchList: List<AndroidDispatchDTO>) : RecyclerView.Adapter<DispatchAdapter.DispatchViewHolder>() {

    // 각 아이템 뷰의 구성 요소를 보유할 ViewHolder 클래스
    inner class DispatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.warehouseItemName) // 상품명 텍스트뷰
        val productQuantity: TextView = itemView.findViewById(R.id.warehouseItemDescription) // 수량 텍스트뷰
        val checkBox: ImageView = itemView.findViewById(R.id.warehouseItemCheckBox) // 체크박스 이미지뷰
    }

    // ViewHolder를 생성하여 아이템 레이아웃을 인플레이트
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_warehouse, parent, false)
        return DispatchViewHolder(view)
    }

    // 각 아이템 뷰에 데이터 바인딩
    override fun onBindViewHolder(holder: DispatchViewHolder, position: Int) {
        val item = dispatchList[position]
        holder.productName.text = item.productNm // 상품명 설정
        holder.productQuantity.text = "${item.orderDQty}개" // 수량 설정

        // 체크 상태에 따라 체크박스 이미지 업데이트
        holder.checkBox.setImageResource(if (item.isChecked) R.drawable.ic_checked else R.drawable.ic_unchecked)
    }

    // 아이템의 총 개수를 반환
    override fun getItemCount(): Int = dispatchList.size

    // 데이터를 업데이트하고 RecyclerView를 갱신하는 메서드
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<AndroidDispatchDTO>) {
        dispatchList = newList
        notifyDataSetChanged() // 데이터 갱신 알림
        Log.d("myLog", "Adapter data updated with ${newList.size} items")
    }

    // QR 코드 스캔 결과로 특정 아이템의 체크 상태를 업데이트하는 메서드
    fun updateItemCheckStatus(productName: String) {
        val index = dispatchList.indexOfFirst { it.productNm == productName } // 상품명으로 아이템 찾기
        if (index != -1) {
            dispatchList[index].isChecked = true // 체크 상태 업데이트
            notifyItemChanged(index) // 해당 아이템만 갱신
            Log.d("myLog", "Item '$productName' checked")
        } else {
            Log.d("myLog", "Item '$productName' not found in the list")
        }
    }
}

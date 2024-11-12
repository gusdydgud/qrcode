package com.example.liststart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.model.AndroidDispatchDTO

// 이미 출고된 목록을 표시하기 위한 RecyclerView 어댑터
class DoneDispatchAdapter(private var doneDispatchList: List<AndroidDispatchDTO>) : RecyclerView.Adapter<DoneDispatchAdapter.DoneDispatchViewHolder>() {

    // 초기 표시할 항목 개수 제한
    private var displayLimit = 4

    // 각 아이템 뷰의 구성 요소를 보유할 ViewHolder 클래스
    inner class DoneDispatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productNameTextView) // 상품명 텍스트뷰
        val productQuantity: TextView = itemView.findViewById(R.id.quantityTextView) // 수량 텍스트뷰
        val customer: TextView = itemView.findViewById(R.id.customerNameTextView) // 회사명 텍스트뷰
        val dispatchNo: TextView = itemView.findViewById(R.id.dispatchNoTextView) // 출고번호 텍스트뷰
    }

    // ViewHolder를 생성하여 아이템 레이아웃을 인플레이트
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneDispatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dispatch, parent, false)
        return DoneDispatchViewHolder(view)
    }

    // 각 아이템 뷰에 데이터 바인딩
    override fun onBindViewHolder(holder: DoneDispatchViewHolder, position: Int) {
        val item = doneDispatchList[position]
        holder.productName.text = item.productNm // 상품명 설정
        holder.productQuantity.text = "${item.orderDQty}개" // 수량 설정
        holder.customer.text = item.customerName // 고객명 설정
        holder.dispatchNo.text = "출고번호 : ${item.dispatchNo}" // 출고번호 설정
    }

    // 표시할 아이템 개수를 제한
    override fun getItemCount(): Int = minOf(displayLimit, doneDispatchList.size)

    // 데이터를 업데이트하고 RecyclerView를 갱신하는 메서드
    fun updateData(newList: List<AndroidDispatchDTO>) {
        doneDispatchList = newList
        displayLimit = minOf(4, newList.size) // 초기 표시 항목 제한
        notifyDataSetChanged() // 데이터 갱신 알림
    }

    // "더보기" 기능을 통해 모든 아이템을 표시하도록 설정
    fun showAllItems() {
        displayLimit = doneDispatchList.size // 모든 항목 표시
        notifyDataSetChanged() // 전체 갱신
    }
}

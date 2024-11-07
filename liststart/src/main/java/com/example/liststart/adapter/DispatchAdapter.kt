package com.example.liststart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.model.Dispatch

class DispatchAdapter(
    private var isVisible: Boolean, // 체크박스 가시성 여부
    private val onItemClick: (Dispatch) -> Unit, // 아이템 클릭 이벤트 콜백
    private val onCheckBoxClick: (Dispatch) -> Unit // 체크박스 클릭 이벤트 콜백
) : RecyclerView.Adapter<DispatchAdapter.DispatchViewHolder>() {

    private var dispatchList: List<Dispatch> = listOf() // Dispatch 리스트

    // 각 아이템 뷰를 관리하는 ViewHolder 클래스
    class DispatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemRoot: View = view // 전체 아이템 뷰
        val warehouseItemName: TextView = view.findViewById(R.id.warehouseItemName) // 상품명 텍스트
        val warehouseItemDescription: TextView = view.findViewById(R.id.warehouseItemDescription) // 수량 텍스트
        val checkBox: ImageView = view.findViewById(R.id.warehouseItemCheckBox) // 체크박스 이미지 뷰
    }

    // 새로운 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispatchViewHolder {
        // layout 파일 이름에 맞게 수정 필요
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_warehouse, parent, false)
        return DispatchViewHolder(view)
    }

    // ViewHolder와 데이터를 바인딩
    override fun onBindViewHolder(holder: DispatchViewHolder, position: Int) {
        val item = dispatchList[position]

        holder.warehouseItemName.text = item.productNm // 상품명 설정
        holder.warehouseItemDescription.text = "${item.orderDQty ?: 0}개" // 수량 설정

        // 체크박스 이미지 설정 (예: 선택 시와 선택 해제 시 이미지)
        holder.checkBox.setImageResource(
            if (item.isChecked) R.drawable.ic_check else R.drawable.ic_unchecked
        )

        // 체크박스 가시성 설정
        holder.checkBox.visibility = if (isVisible) View.VISIBLE else View.GONE

        // 아이템 클릭 처리
        holder.itemRoot.setOnClickListener {
            onItemClick(item) // 아이템 클릭 시 이벤트 호출
        }

        // 체크박스 클릭 처리
        holder.checkBox.setOnClickListener {
            item.isChecked = !item.isChecked // 체크 상태 토글
            notifyItemChanged(position) // UI 업데이트
            onCheckBoxClick(item) // 체크박스 클릭 이벤트 처리
        }
    }

    // 아이템 개수를 반환
    override fun getItemCount(): Int {
        return dispatchList.size
    }

    // 새로운 리스트를 설정하고 UI 갱신
    fun updateList(newList: List<Dispatch>) {
        dispatchList = newList
        notifyDataSetChanged()
    }

    // 체크박스 가시성 변경
    fun updateVisibility(visible: Boolean) {
        isVisible = visible
        notifyDataSetChanged()
    }
}

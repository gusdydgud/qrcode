package com.example.liststart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AndroidDispatchDTO(
    val dispatchNo: Int?,        // 출고 번호
    val productNm: String?,      // 상품명
    val orderDQty: Int?,          // 수량 (Int에서 Int?로 변경)
    var isChecked: Boolean = false,
    val customerName: String?
) : Parcelable
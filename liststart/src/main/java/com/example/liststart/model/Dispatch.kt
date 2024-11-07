package com.example.liststart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.UUID

@Parcelize
data class Dispatch(
    val dispatchNo: Int,                     // 출고 번호
    val dispatchStatus: String,              // 출고 상태
    val warehouseName: String?,              // 출고 창고명
    val qrCodeId: UUID?,                     // 출고 QR 코드 ID
    val productNm: String?,                  // 상품명
    val orderDQty: Int?,                     // 수량
    var isChecked: Boolean = false
) : Parcelable
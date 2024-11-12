package com.example.liststart.datasource

import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.Response

interface DispatchDataSource {
    suspend fun getDispatchList(warehouseNo: Int): Response<List<AndroidDispatchDTO>>
    suspend fun updateDispatch(dispatchNo: Int): Response<Void> // 하나의 dispatchNo에 대해 출고 상태를 업데이트하는 메서드
    suspend fun getHistroy(warehouseNo: Int, daysAgo: Int): Response<List<AndroidDispatchDTO>>

}
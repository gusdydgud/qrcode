package com.example.liststart.datasource

import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.Response

interface DispatchDataSource {
    suspend fun getDispatchList(warehouseNo: Int): Response<List<AndroidDispatchDTO>>
//    suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch>

}
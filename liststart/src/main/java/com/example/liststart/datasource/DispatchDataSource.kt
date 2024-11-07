package com.example.liststart.datasource

import com.example.liststart.model.Dispatch
import retrofit2.Response

interface DispatchDataSource {
    suspend fun getDispatchList(): Response<List<Dispatch>>
    suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch>
}
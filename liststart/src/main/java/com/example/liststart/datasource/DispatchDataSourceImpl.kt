package com.example.liststart.datasource

import com.example.liststart.model.Dispatch
import com.example.liststart.service.DispatchAPIService
import retrofit2.Response

class DispatchDataSourceImpl(private val apiService: DispatchAPIService) : DispatchDataSource {

    override suspend fun getDispatchList(): Response<List<Dispatch>> {
        return apiService.getDispatchAll()
    }
    override suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch> {
        return apiService.updateDispatch(bno, dispatch)
    }
}
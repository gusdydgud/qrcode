package com.example.liststart.datasource
import com.example.liststart.model.AndroidDispatchDTO

import com.example.liststart.service.DispatchAPIService
import retrofit2.Response

class DispatchDataSourceImpl(private val apiService: DispatchAPIService) : DispatchDataSource {


    override suspend fun getDispatchList(warehouseNo: Int): Response<List<AndroidDispatchDTO>> {
        return apiService.getDispatchAll(warehouseNo)
    }
    override suspend fun updateDispatch(dispatchNo: Int): Response<Void> {
        return apiService.updateDispatch(dispatchNo)
    }

}
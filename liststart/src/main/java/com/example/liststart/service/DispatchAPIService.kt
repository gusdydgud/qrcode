package com.example.liststart.service

import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DispatchAPIService {
    @GET("get/dispatch/inProgress/{warehouseNo}")
    suspend fun getDispatchAll(@Path("warehouseNo") warehouseNo: Int): Response<List<AndroidDispatchDTO>>
}

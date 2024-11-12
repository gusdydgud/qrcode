package com.example.liststart.service

import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DispatchAPIService {
    @GET("get/dispatch/inProgress/{warehouseNo}")
    suspend fun getDispatchAll(@Path("warehouseNo") warehouseNo: Int): Response<List<AndroidDispatchDTO>>

    @POST("update/dispatch/complete/{dispatchNo}")
    suspend fun updateDispatch(@Path("dispatchNo") dispatchNo: Int): Response<Void>
    @GET("get/dispatch/completed/{warehouseNo}/{daysAgo}")
    suspend fun getDispatchAll(
        @Path("warehouseNo") warehouseNo: Int,
        @Path("daysAgo") daysAgo: Int
    ): Response<List<AndroidDispatchDTO>>
}

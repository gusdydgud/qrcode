package com.example.liststart.service

import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path

interface DispatchAPIService {

    @GET("get/dispatch/all/{warehouseNo}")
    suspend fun getDispatchAll(@Path("warehouseNo") warehouseNo: Int): Response<List<AndroidDispatchDTO>>

    @POST("post/dispatch/update/{dispatchNo}")
    suspend fun updateDispatch(@Path("dispatchNo") dispatchNo: Int, @Body dispatch: AndroidDispatchDTO): Response<AndroidDispatchDTO>
}

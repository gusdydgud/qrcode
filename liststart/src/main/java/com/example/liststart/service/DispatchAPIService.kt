package com.example.liststart.service

import com.example.liststart.model.Dispatch
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DispatchAPIService {

    @Headers("Content-Type: application/json")
    @GET("get/dispatch/all")
    suspend fun getDispatchAll(): Response<List<Dispatch>>

    @Headers("Content-Type: application/json")
    @POST("post/dispatch/update/{bno}")
    suspend fun updateDispatch(@Path(value = "bno") bno: Long, @Body dispatch: Dispatch): Response<Dispatch>
}
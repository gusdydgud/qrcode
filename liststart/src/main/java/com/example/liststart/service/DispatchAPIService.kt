package com.example.liststart.service

<<<<<<< HEAD
import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
=======
import com.example.liststart.model.Dispatch
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.DELETE
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
import retrofit2.http.Path

interface DispatchAPIService {

<<<<<<< HEAD
    @GET("get/dispatch/all/{warehouseNo}")
    suspend fun getDispatchAll(@Path("warehouseNo") warehouseNo: Int): Response<List<AndroidDispatchDTO>>


    @POST("post/dispatch/update/{dispatchNo}")
    suspend fun updateDispatch(@Path("dispatchNo") dispatchNo: Int, @Body dispatch: AndroidDispatchDTO): Response<AndroidDispatchDTO>
}

=======
    @Headers("Content-Type: application/json")
    @GET("get/dispatch/all")
    suspend fun getDispatchAll(): Response<List<Dispatch>>

    @Headers("Content-Type: application/json")
    @POST("post/dispatch/update/{bno}")
    suspend fun updateDispatch(@Path(value = "bno") bno: Long, @Body dispatch: Dispatch): Response<Dispatch>
}
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f

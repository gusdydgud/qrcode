package com.example.liststart.datasource

<<<<<<< HEAD
import com.example.liststart.model.AndroidDispatchDTO
import retrofit2.Response

interface DispatchDataSource {
    suspend fun getDispatchList(warehouseNo: Int): Response<List<AndroidDispatchDTO>>
//    suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch>
=======
import com.example.liststart.model.Dispatch
import retrofit2.Response

interface DispatchDataSource {
    suspend fun getDispatchList(): Response<List<Dispatch>>
    suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch>
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
}
package com.example.liststart.datasource

<<<<<<< HEAD
import com.example.liststart.model.AndroidDispatchDTO
=======
import com.example.liststart.model.Dispatch
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
import com.example.liststart.service.DispatchAPIService
import retrofit2.Response

class DispatchDataSourceImpl(private val apiService: DispatchAPIService) : DispatchDataSource {

<<<<<<< HEAD
    override suspend fun getDispatchList(warehouseNo: Int): Response<List<AndroidDispatchDTO>> {
        return apiService.getDispatchAll(warehouseNo)
    }
//    override suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch> {
//        return apiService.updateDispatch(bno, dispatch)
//    }
=======
    override suspend fun getDispatchList(): Response<List<Dispatch>> {
        return apiService.getDispatchAll()
    }
    override suspend fun updateDispatch(bno: Long, dispatch: Dispatch): Response<Dispatch> {
        return apiService.updateDispatch(bno, dispatch)
    }
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
}
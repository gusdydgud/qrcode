package com.example.liststart.datasource

<<<<<<< HEAD
import com.example.liststart.service.DispatchAPIService
import com.example.liststart.util.QRConstants
import com.example.liststart.viewmodel.DispatchViewModelFactory

object DataSourceProvider {

    // Dispatch 관련 싱글톤 인스턴스
    private val dispatchDataSource: DispatchDataSource by lazy {
        DispatchDataSourceImpl(QRConstants.dispatchApiService)
    }

    val dispatchViewModelFactory: DispatchViewModelFactory by lazy {
        DispatchViewModelFactory(dispatchDataSource)
    }
}
=======
import com.example.liststart.util.Constants
import com.example.liststart.viewmodel.BusinessViewModelFactory
import com.example.liststart.viewmodel.MarkerViewModelFactory

object DataSourceProvider {
    // Business 관련 싱글톤 인스턴스
    private val businessDataSource: BusinessDataSource by lazy {
        BusinessDataSourceImpl(Constants.turbineApiService)
    }

    val businessViewModelFactory: BusinessViewModelFactory by lazy {
        BusinessViewModelFactory(businessDataSource)
    }

    private val markerDataSource: MarkerDataSource by lazy {
        MarkerDataSourceImpl(Constants.turbineApiService)
    }

    val markerViewModelFactory: MarkerViewModelFactory by lazy {
        MarkerViewModelFactory(markerDataSource)
    }

}
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f

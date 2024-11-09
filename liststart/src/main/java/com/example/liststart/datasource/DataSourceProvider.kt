package com.example.liststart.datasource

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


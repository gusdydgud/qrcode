package com.example.liststart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liststart.datasource.DispatchDataSource
import com.example.liststart.model.AndroidDispatchDTO

import kotlinx.coroutines.launch

class DispatchViewModel(private val dataSource: DispatchDataSource) : ViewModel() {

    private val _dispatchList = MutableLiveData<List<AndroidDispatchDTO>>()
    val dispatchList: LiveData<List<AndroidDispatchDTO>> = _dispatchList

    // 서버에서 데이터 로드
    fun loadDispatchList(warehouseNo: Int) {
        Log.d("myLog", "loadDispatchList called with warehouseNo: $warehouseNo")
        viewModelScope.launch {
            val response = dataSource.getDispatchList(warehouseNo)
            if (response.isSuccessful) {
                _dispatchList.value = response.body()
                Log.d("myLog", "Data loaded successfully: ${response.body()}")
            } else {
                _dispatchList.value = emptyList()
                Log.e("myLog", "Failed to load data: ${response.errorBody()?.string()}")
            }
        }
    }
}

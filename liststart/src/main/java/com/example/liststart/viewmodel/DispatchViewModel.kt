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

    // 출고 상태 업데이트 결과를 저장할 LiveData
    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    // 서버에서 Dispatch 데이터 로드
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

    // 출고 상태를 업데이트하는 메서드
    fun updateDispatch(dispatchNos: List<Int>) {
        viewModelScope.launch {
            try {
                var allSuccessful = true
                for (dispatchNo in dispatchNos) {
                    val response = dataSource.updateDispatch(dispatchNo)
                    if (!response.isSuccessful) {
                        allSuccessful = false
                        Log.e("DispatchViewModel", "Failed to update dispatchNo: $dispatchNo")
                        break
                    }
                }
                _updateResult.postValue(allSuccessful)
            } catch (e: Exception) {
                _updateResult.postValue(false)
                Log.e("DispatchViewModel", "Error updating dispatch status", e)
            }
        }
    }


}

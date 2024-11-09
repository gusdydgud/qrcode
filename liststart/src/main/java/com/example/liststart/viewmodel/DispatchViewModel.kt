package com.example.liststart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liststart.datasource.DispatchDataSource
import com.example.liststart.model.AndroidDispatchDTO
import kotlinx.coroutines.launch

class DispatchViewModel(private val dispatchDataSource: DispatchDataSource) : ViewModel() {

    // Dispatch 목록을 저장하는 LiveData
    private val _dispatchList = MutableLiveData<List<AndroidDispatchDTO>?>()
    val dispatchList: LiveData<List<AndroidDispatchDTO>?> get() = _dispatchList

    // 완료된 Dispatch 목록을 저장하는 LiveData
    private val _completedDispatchList = MutableLiveData<List<AndroidDispatchDTO>>()
    val completedDispatchList: LiveData<List<AndroidDispatchDTO>> get() = _completedDispatchList

    // 에러 메시지를 전달하기 위한 LiveData
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Dispatch 목록을 로드하는 함수 (창고 번호에 따라 로드)
    fun loadDispatchList(warehouseNo: Int) {
        viewModelScope.launch {
            Log.d("DispatchViewModel", "loadDispatchList called with warehouseNo: $warehouseNo")
            try {
                val response = dispatchDataSource.getDispatchList(warehouseNo)
                if (response.isSuccessful) {
                    val dispatches = response.body()
                    if (dispatches != null) {
                        _dispatchList.value = dispatches
                        Log.d("DispatchViewModel", "Dispatch list loaded successfully: ${dispatches.size} items")
                    } else {
                        Log.e("DispatchViewModel", "Empty response body received")
                        _error.value = "Empty response body received"
                    }
                } else {
                    Log.e("DispatchViewModel", "Failed to load data: ${response.message()}")
                    _error.value = "Failed to load data: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("DispatchViewModel", "Error loading data: ${e.localizedMessage}")
                _error.value = "Error loading data: ${e.localizedMessage}"
            }
        }
    }

    // Dispatch의 체크 상태를 토글하는 함수
//    fun toggleDispatchChecked(dispatch: AndroidDispatchDTO) {
//        // 업데이트된 Dispatch 목록을 반영
//        _dispatchList.value = _dispatchList.value?.map {
//            if (it.dispatchNo == dispatch.dispatchNo) dispatch.copy(isChecked = !dispatch.isChecked) else it
//        }
//        updateCompletedDispatches() // 완료된 항목 업데이트
//    }

    // 완료된 Dispatch 목록을 업데이트하는 함수
//    private fun updateCompletedDispatches() {
//        _completedDispatchList.value = _dispatchList.value?.filter { it.isChecked }
//    }

    // Dispatch를 업데이트하는 함수
//    fun updateDispatch(dispatch: AndroidDispatchDTO) {
//        viewModelScope.launch {
//            Log.d("DispatchViewModel", "updateDispatch called for dispatchNo: ${dispatch.dispatchNo}")
//            try {
//                val response = dispatchDataSource.updateDispatch(dispatch.dispatchNo.toLong(), dispatch)
//                if (response.isSuccessful) {
//                    val updatedDispatch = response.body()
//                    if (updatedDispatch != null) {
//                        val updatedList = _dispatchList.value?.map {
//                            if (it.dispatchNo == updatedDispatch.dispatchNo) updatedDispatch else it
//                        }
//                        _dispatchList.value = updatedList
//                        updateCompletedDispatches() // 완료된 항목 업데이트
//                        Log.d("DispatchViewModel", "Dispatch updated successfully for dispatchNo: ${dispatch.dispatchNo}")
//                    } else {
//                        Log.e("DispatchViewModel", "Failed to update dispatch: Empty response body")
//                        _error.value = "Failed to update dispatch: Empty response body"
//                    }
//                } else {
//                    Log.e("DispatchViewModel", "Failed to update dispatch: ${response.message()}")
//                    _error.value = "Failed to update dispatch: ${response.message()}"
//                }
//            } catch (e: Exception) {
//                Log.e("DispatchViewModel", "Error updating dispatch: ${e.localizedMessage}")
//                _error.value = "Error updating dispatch: ${e.localizedMessage}"
//            }
//        }
//    }
}

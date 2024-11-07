package com.example.liststart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liststart.datasource.DispatchDataSource
import com.example.liststart.model.Dispatch
import kotlinx.coroutines.launch

class DispatchViewModel(private val dispatchDataSource: DispatchDataSource) : ViewModel() {

    // Dispatch List
    private val _dispatchList = MutableLiveData<List<Dispatch>>()
    val dispatchList: LiveData<List<Dispatch>> get() = _dispatchList

    // 완료된 Dispatch 목록
    private val _completedDispatchList = MutableLiveData<List<Dispatch>>()
    val completedDispatchList: LiveData<List<Dispatch>> get() = _completedDispatchList

    // 에러 메시지 전달을 위한 LiveData
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Dispatch 목록 로드
    fun loadDispatchList() {
        viewModelScope.launch {
            try {
                val response = dispatchDataSource.getDispatchList()
                if (response.isSuccessful && response.body() != null) {
                    _dispatchList.value = response.body()
                    updateCompletedDispatches() // 완료된 항목 업데이트
                } else {
                    _error.value = "데이터를 가져오는 데 실패했습니다: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "데이터를 가져오는 중 오류가 발생했습니다: ${e.localizedMessage}"
            }
        }
    }

    // Dispatch 체크 상태 토글
    fun toggleDispatchChecked(dispatch: Dispatch) {
        dispatch.isChecked = !dispatch.isChecked
        updateCompletedDispatches() // 완료된 항목 업데이트
    }

    // 완료된 Dispatch 목록 업데이트
    private fun updateCompletedDispatches() {
        _completedDispatchList.value = _dispatchList.value?.filter { it.isChecked }
    }

    // Dispatch 업데이트
    fun updateDispatch(dispatch: Dispatch) {
        viewModelScope.launch {
            try {
                val response = dispatchDataSource.updateDispatch(dispatch.dispatchNo.toLong(), dispatch)
                if (response.isSuccessful) {
                    val updatedDispatch = response.body()
                    if (updatedDispatch != null) {
                        val updatedList = _dispatchList.value?.map {
                            if (it.dispatchNo == updatedDispatch.dispatchNo) updatedDispatch else it
                        }
                        _dispatchList.value = updatedList
                        updateCompletedDispatches() // 완료된 항목 업데이트
                    }
                } else {
                    _error.value = "출고 항목을 업데이트하는 데 실패했습니다: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "출고 항목 업데이트 중 오류가 발생했습니다: ${e.localizedMessage}"
            }
        }
    }
}

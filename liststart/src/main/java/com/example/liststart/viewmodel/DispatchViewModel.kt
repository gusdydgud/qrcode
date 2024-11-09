package com.example.liststart.viewmodel

<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liststart.datasource.DispatchDataSource
<<<<<<< HEAD
import com.example.liststart.model.AndroidDispatchDTO
=======
import com.example.liststart.model.Dispatch
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
import kotlinx.coroutines.launch

class DispatchViewModel(private val dispatchDataSource: DispatchDataSource) : ViewModel() {

<<<<<<< HEAD
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
=======
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
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
            }
        }
    }

<<<<<<< HEAD
    // Dispatch의 체크 상태를 토글하는 함수
//    fun toggleDispatchChecked(dispatch: Dispatch) {
//        dispatch.isChecked = !dispatch.isChecked
//        updateCompletedDispatches() // 완료된 항목 업데이트
//    }
//
//    // 완료된 Dispatch 목록을 업데이트하는 함수
//    private fun updateCompletedDispatches() {
//        _completedDispatchList.value = _dispatchList.value?.filter { it.isChecked }
//    }

    // Dispatch를 업데이트하는 함수
//    fun updateDispatch(dispatch: Dispatch) {
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
=======
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
>>>>>>> 3443f5f092f6b9bfd56a6e67cc8f12c502601a8f
}

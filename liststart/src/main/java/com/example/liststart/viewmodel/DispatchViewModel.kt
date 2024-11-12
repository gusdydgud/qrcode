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

    private val _doneDispatchList1Day = MutableLiveData<List<AndroidDispatchDTO>>()
    val doneDispatchList1Day: LiveData<List<AndroidDispatchDTO>> = _doneDispatchList1Day

    private val _doneDispatchList2Days = MutableLiveData<List<AndroidDispatchDTO>>()
    val doneDispatchList2Days: LiveData<List<AndroidDispatchDTO>> = _doneDispatchList2Days

    private val _doneDispatchList3Days = MutableLiveData<List<AndroidDispatchDTO>>()
    val doneDispatchList3Days: LiveData<List<AndroidDispatchDTO>> = _doneDispatchList3Days

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    // 일반 출고 목록 로드
    fun loadDispatchList(warehouseNo: Int) {
        viewModelScope.launch {
            val response = dataSource.getDispatchList(warehouseNo)
            _dispatchList.value = response.body() ?: emptyList()
        }
    }

    // 완료된 출고 목록 로드
    fun loadDoneDispatchList(warehouseNo: Int) {
        viewModelScope.launch {
            loadDoneDispatchForDaysAgo(warehouseNo, 1, _doneDispatchList1Day)
            loadDoneDispatchForDaysAgo(warehouseNo, 2, _doneDispatchList2Days)
            loadDoneDispatchForDaysAgo(warehouseNo, 3, _doneDispatchList3Days)
        }
    }

    private suspend fun loadDoneDispatchForDaysAgo(
        warehouseNo: Int,
        daysAgo: Int,
        liveData: MutableLiveData<List<AndroidDispatchDTO>>
    ) {
        val response = dataSource.getHistroy(warehouseNo, daysAgo)
        liveData.value = response.body() ?: emptyList()
    }

    fun updateDispatch(dispatchNos: List<Int>) {
        viewModelScope.launch {
            var allSuccessful = true
            for (dispatchNo in dispatchNos) {
                val response = dataSource.updateDispatch(dispatchNo)
                if (!response.isSuccessful) {
                    allSuccessful = false
                    break
                }
            }
            _updateResult.postValue(allSuccessful)
        }
    }
}

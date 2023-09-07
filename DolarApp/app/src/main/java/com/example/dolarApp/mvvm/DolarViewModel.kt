package com.example.dolarApp.mvvm

import android.util.Log
import androidx.lifecycle.*
import com.example.dolarApp.mvvm.model.ValueModel
import com.example.dolarApp.mvvm.model.ValueSearchBody
import com.example.dolarApp.mvvm.model.ValueSearchResponse
import com.example.dolarApp.mvvm.repository.DolarRepository
import kotlinx.coroutines.launch

class DolarViewModel(private val repo: DolarRepository): ViewModel() {

    private val _dolarValueList = MutableLiveData<List<ValueModel>>()
    val dolarValueList: LiveData<List<ValueModel>>
        get() = _dolarValueList

    private val _searchValueList = MutableLiveData<ValueSearchResponse>()
    val searchValueList: LiveData<ValueSearchResponse>
        get() = _searchValueList

    init {
        _dolarValueList.value = mutableListOf()
    }

    suspend fun fetchGetDolarValue() {
        viewModelScope.launch {
            try {
                _dolarValueList.postValue(repo.getDolarValue())
            } catch (error: java.lang.Exception) {
                Log.e("error getdolarvaluelist", error.toString())
            }
        }
    }

    suspend fun fetchGetCurrencyValue() {
        viewModelScope.launch {
            try {
                _dolarValueList.postValue(repo.getCurrencyValue())
            } catch (error: java.lang.Exception) {
                Log.e("error getdolarvaluelist", error.toString())
            }
        }
    }

    suspend fun fetchGetValueSearch(body: ValueSearchBody) {
        viewModelScope.launch {
            try {
                _searchValueList.postValue(repo.getValueSearch(body))
            } catch (error: java.lang.Exception) {
                Log.e("error getdolarvaluelist", error.toString())
            }
        }
    }

}

class DolarModelFactory(private val repo: DolarRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DolarRepository::class.java).newInstance(repo)
    }
}
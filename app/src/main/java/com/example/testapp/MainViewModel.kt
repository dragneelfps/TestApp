package com.example.testapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.api.StatesService
import com.example.testapp.data.CovidItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _covidState = MutableStateFlow<CovidState>(CovidState.Loading)
    val covidState = _covidState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun load() {
        _covidState.value = CovidState.Loading
        viewModelScope.launch {
            _covidState.value = CovidState.Success(StatesService.getData())
//            _covidState.value = try {
//                CovidState.Success(StatesService.getData())
//            } catch (e: Throwable) {
//                CovidState.Error(e)
//            }
        }
    }

    fun search(text: String) {
        _searchText.value = text
        Log.d("ASD", "searching for $text")
    }
}

sealed class CovidState {
    object Loading : CovidState()
    class Success(val info: List<CovidItem>) : CovidState()
    class Error(val exception: Throwable) : CovidState()
}
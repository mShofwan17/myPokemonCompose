package me.project.shared.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg get() = _errorMsg

    private val _loading = MutableStateFlow(true)
    val loading get() = _loading

    fun showLoading() {
        viewModelScope.launch {
            _loading.emit(true)
        }
    }

    fun errorMessage(throwable: Throwable) {
        viewModelScope.launch {
            throwable.message?.let { msg ->
                _errorMsg.emit(msg)
            }
        }
    }

    fun resetBaseState() {
        viewModelScope.launch {
            _loading.emit(false)
            _errorMsg.emit(null)
        }
    }
}
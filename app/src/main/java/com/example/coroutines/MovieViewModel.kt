package com.example.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieViewModel:ViewModel() {
    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage:MutableStateFlow<String> = _errorMessage
    private val _movieList = MutableStateFlow<List<Movie>>(listOf())
    val movieList:MutableStateFlow<List<Movie>> = _movieList
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = RetrofitService.getInstance().getAllMovies()
            if (response.isSuccessful) {
                _movieList.emit(response.body()!!)
            } else {
                onError("Error : ${response.message()} ")
            }
        }

    }

    private fun onError(message: String) {
        _errorMessage.value = message
    }

}
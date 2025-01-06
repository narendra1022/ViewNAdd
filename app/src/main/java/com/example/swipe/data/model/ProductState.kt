package com.example.swipe.data.model

sealed class ProductState {
    object Initial : ProductState()
    object Loading : ProductState()
    data class Success(val message: String) : ProductState()
    data class Error(val message: String) : ProductState()
}
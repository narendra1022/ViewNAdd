package com.example.swipe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.data.Repository.ProductRepository
import com.example.swipe.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val filteredProducts = combine(products, searchQuery) { products, query ->
        if (query.isBlank()) products
        else products.filter {
            it.product_name.contains(query, ignoreCase = true)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                repository.getProducts()
                    .catch { e ->
                        _errorMessage.value = getErrorMessage(e)
                        _isLoading.value = false
                    }
                    .collect { result ->
                        result.onSuccess { products ->
                            _products.value = products
                            _errorMessage.value = null
                        }.onFailure { e ->
                            _errorMessage.value = getErrorMessage(e)
                        }
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _errorMessage.value = getErrorMessage(e)
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> "No internet connection"
            is SocketTimeoutException -> "Connection timed out"
            is ConnectException -> "No internet connection"
            is IOException -> "Network error occurred"
            else -> throwable.message ?: "Unknown error occurred"
        }
    }
}
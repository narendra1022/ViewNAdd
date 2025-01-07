package com.example.swipe.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.data.Repository.ProductRepository
import com.example.swipe.data.model.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProductState>(ProductState.Initial)
    val state: StateFlow<ProductState> = _state.asStateFlow()


    private val _productTypes = MutableStateFlow(
        listOf(
            "Electronics",
            "Clothing",
            "Food",
            "Books",
            "Others"
        )
    )
    val productTypes: StateFlow<List<String>> = _productTypes.asStateFlow()

    fun addProduct(
        productName: String,
        productType: String,
        price: Double,
        tax: Double,
        images: List<Uri>?
    ) {
        viewModelScope.launch {
            _state.value = ProductState.Loading
            try {
                repository.addProduct(productName, productType, price, tax, images)
                    .catch { e ->
                        _state.value = ProductState.Error(getErrorMessage(e))
                    }
                    .collect { result ->
                        _state.value = when {
                            result.isSuccess -> ProductState.Success(
                                result.getOrNull()?.message ?: "Product added successfully"
                            )

                            result.isFailure -> ProductState.Error(
                                getErrorMessage(
                                    result.exceptionOrNull() ?: Exception("Unknown error occurred")
                                )
                            )

                            else -> ProductState.Error("Unknown error occurred")
                        }
                    }
            } catch (e: Exception) {
                _state.value = ProductState.Error(getErrorMessage(e))
            }
        }
    }
}

fun getErrorMessage(throwable: Throwable): String {
    return when (throwable) {
        is UnknownHostException -> "No internet connection"
        is SocketTimeoutException -> "Connection timed out"
        is ConnectException -> "No internet connection"
        is IOException -> "Network error occurred"
        else -> throwable.message ?: "Unknown error occurred"
    }
}
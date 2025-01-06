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
import kotlinx.coroutines.launch
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
            "Other"
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
            repository.addProduct(productName, productType, price, tax, images)
                .collect { result ->
                    _state.value = when {
                        result.isSuccess -> ProductState.Success(
                            result.getOrNull()?.message ?: "Product added successfully"
                        )

                        result.isFailure -> ProductState.Error(
                            result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        )

                        else -> ProductState.Error("Unknown error occurred")
                    }
                }
        }
    }
}
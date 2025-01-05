package com.example.swipe.data.Repository

import com.example.swipe.data.model.Product
import com.example.swipe.data.network.ProductApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ProductApi
) {
    suspend fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val response = api.getProducts()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

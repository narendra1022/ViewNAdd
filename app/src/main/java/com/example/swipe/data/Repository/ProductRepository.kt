package com.example.swipe.data.Repository

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.swipe.data.model.Product
import com.example.swipe.data.model.ProductResponse
import com.example.swipe.data.network.ProductApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val api: ProductApi,
    @ApplicationContext private val context: Context
) {

    fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val response = api.getProducts()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun addProduct(
        productName: String,
        productType: String,
        price: Double,
        tax: Double,
        images: List<Uri>?
    ): Flow<Result<ProductResponse>> = flow {
        try {
            emit(Result.success(api.addProduct(
                productName.toRequestBody("text/plain".toMediaType()),
                productType.toRequestBody("text/plain".toMediaType()),
                price.toString().toRequestBody("text/plain".toMediaType()),
                tax.toString().toRequestBody("text/plain".toMediaType()),
                images?.map { uri ->
                    val stream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(stream)
                    val squareBitmap = createSquareBitmap(bitmap)
                    val file = createTempFile(context, squareBitmap)

                    MultipartBody.Part.createFormData(
                        "files[]",
                        file.name,
                        file.asRequestBody("image/*".toMediaType())
                    )
                }
            )))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }


    }
}
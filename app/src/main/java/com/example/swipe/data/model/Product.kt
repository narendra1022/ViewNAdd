package com.example.swipe.data.model

data class Product(
    val image: String?,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
){
    companion object {
        fun empty() = Product(
            image = null,
            price = 0.0,
            product_name = "",
            product_type = "",
            tax = 0.0
        )
    }
}
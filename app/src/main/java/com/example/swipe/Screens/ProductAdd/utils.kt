package com.example.swipe.Screens.ProductAdd

import com.example.swipe.data.model.Product

fun validateInput(input: Product): Boolean {
    return input.product_name.isNotBlank() &&
            input.product_type.isNotBlank() &&
            input.price > 0 &&
            input.tax >= 0
}
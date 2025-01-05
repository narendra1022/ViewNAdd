package com.example.swipe.navigation

sealed class Routes(val route: String) {
    object ProductList : Routes("productList")
    object AddProduct : Routes("addProduct")
}
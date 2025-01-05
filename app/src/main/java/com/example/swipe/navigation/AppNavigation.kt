package com.example.swipe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swipe.Screens.ProductAdd.AddProductScreen
import com.example.swipe.Screens.ProductList.ProductListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.ProductList.route) {
        composable(Routes.ProductList.route) {
            ProductListScreen(
                navigateToAddProduct = {
                    navController.navigate(Routes.AddProduct.route)
                }
            )
        }
        composable(Routes.AddProduct.route) {
            AddProductScreen()
        }
    }
}
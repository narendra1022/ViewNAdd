package com.example.swipe.Screens.ProductList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.swipe.viewmodels.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    navigateToAddProduct: () -> Unit
) {
    val products by viewModel.filteredProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState(initial = null)

    var isSearchExpanded by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddProduct,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Add Product")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Animated Search Bar
            AnimatedSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::updateSearchQuery,
                isExpanded = isSearchExpanded,
                onExpandedChange = { isSearchExpanded = it }
            )

            // Error Handling
            errorMessage?.let { message ->
                ErrorBanner(message = message)
            }

            // Products List with loading and empty states
            ProductsList(
                products = products,
                isLoading = isLoading
            )
        }
    }
}
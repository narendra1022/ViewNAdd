package com.example.swipe.Screens.ProductAdd

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swipe.data.model.ProductState
import com.example.swipe.viewmodels.AddProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val productTypes by viewModel.productTypes.collectAsState()
    val scope = rememberCoroutineScope()

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AddProductTopBar(onNavigateBack = { navController.popBackStack() })
        }
    ) { paddingValues ->
        AddProductContent(
            modifier = Modifier.padding(paddingValues),
            productTypes = productTypes,
            isLoading = state is ProductState.Loading,
            onAddProduct = { product, images ->
                viewModel.addProduct(
                    product.product_name,
                    product.product_type,
                    product.price,
                    product.tax,
                    images
                )
                scope.launch {
                    delay(2000)
                    navController.popBackStack()
                }

            },
            onValidationError = { message ->
                errorMessage = message
                showErrorDialog = true
            }
        )
    }

    // Handle states
    LaunchedEffect(state) {
        when (state) {
            is ProductState.Success -> {
                Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
            }

            is ProductState.Error -> {
                errorMessage = (state as ProductState.Error).message
                showErrorDialog = true
            }

            else -> {}
        }
    }

    if (showErrorDialog) {
        ErrorDialog(
            message = errorMessage,
            onDismiss = { showErrorDialog = false }
        )
    }
}

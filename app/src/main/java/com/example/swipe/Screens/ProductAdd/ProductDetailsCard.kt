package com.example.swipe.Screens.ProductAdd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swipe.data.model.Product

@Composable
fun ProductDetailsCard(
    product: Product,
    priceText: String,
    taxText: String,
    productTypes: List<String>,
    onProductChange: (Product) -> Unit,
    onPriceTextChange: (String) -> Unit,
    onTaxTextChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = product.product_name,
                onValueChange = { onProductChange(product.copy(product_name = it)) },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = product.product_name.isBlank(),
                supportingText = {
                    if (product.product_name.isBlank()) {
                        Text("Product name is required")
                    }
                }
            )

            ProductTypeDropdown(
                selectedType = product.product_type,
                productTypes = productTypes,
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onTypeSelected = { onProductChange(product.copy(product_type = it)) }
            )

            NumberInputField(
                value = priceText,
                onValueChange = onPriceTextChange,
                label = "Price",
                isError = priceText.isNotEmpty() && priceText.toDoubleOrNull() == null
            )

            NumberInputField(
                value = taxText,
                onValueChange = onTaxTextChange,
                label = "Tax Rate",
                isError = taxText.isNotEmpty() && taxText.toDoubleOrNull() == null
            )
        }
    }
}

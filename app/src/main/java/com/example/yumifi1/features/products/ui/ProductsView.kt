package com.example.yumifi1.features.products.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.products.ui.components.AddProductComponent
import com.example.yumifi1.features.products.ui.components.ProductItemComponent
import com.example.yumifi1.features.products.ui.event.ProductsEvent
import com.example.yumifi1.navigation.Screen

@Composable
fun ProductsView(
    viewModel: ProductsViewModel = hiltViewModel(),
    openView: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is ProductsEvent.OpenAuthView -> {
                    openView(Screen.Auth)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ToolbarComponent(
            onLogoutClicked = viewModel::onLogoutClicked
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProductsContentComponent(
            products = state.products,
            openDetails = { productId ->
                openView(Screen.ProductDetails(productId))
            },
            onDeleteClicked = viewModel::onDeleteClicked
        )
    }
}

@Composable
private fun ToolbarComponent(
    onLogoutClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.products),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(
                    alignment = Alignment.Center,
                )
        )
        IconButton(
            onClick = onLogoutClicked,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterEnd,
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ProductsContentComponent(
    products: List<Product>,
    openDetails: (Int?) -> Unit,
    onDeleteClicked: (Product) -> Unit,
) {
    LazyColumn {
        item {
            AddProductComponent(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                )
            ) { openDetails(null) }
        }
        items(
            count = products.size
        ) { index ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            ProductItemComponent(
                product = products[index],
                modifier = Modifier,
                onItemClicked = { openDetails(products[index].id) },
                onDeleteClicked = { onDeleteClicked(products[index]) }
            )
        }
    }
}
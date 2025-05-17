package com.example.yumifi1.features.product_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import com.example.yumifi1.features.product_details.ui.event.ProductDetailsEvent
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.product_details.ui.model.ProductUnit

@Composable
fun ProductDetailsView(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    back: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is ProductDetailsEvent.OnBackNavigate -> {
                    back()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ToolbarComponent { back() }
        ContentComponent(
            state = state,
            onNameChanged = viewModel::onNameChanged,
            onSaveClicked = viewModel::onSaveClicked,
            onUnitClicked = viewModel::onUnitClicked
        )
    }
}

@Composable
private fun ToolbarComponent(
    back: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { back() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(id = R.string.product_details_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(
                alignment = Alignment.Center,
            )
        )
    }
}

@Composable
private fun ContentComponent(
    state: ProductDetailsState,
    onNameChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onUnitClicked: (ProductUnit) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TextField(
            value = state.product.name,
            label = {
                Text(text = stringResource(id = R.string.product_name_label))
            },
            onValueChange = onNameChanged,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth(),
        )
        ProductUnitComponent(
            product = state.product,
            onClicked = onUnitClicked
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onSaveClicked,
            modifier = Modifier
                .fillMaxWidth(),
            enabled = !state.isLoading,
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
private fun ProductUnitComponent(
    product: Product,
    onClicked: (ProductUnit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.product_unit_label)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Box {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(
                        onClick = { expanded = !expanded }
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    )
            ) {
                Text(
                    text = product.unit.value
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                ProductUnit.entries.forEach { unit ->
                    DropdownMenuItem(
                        text = {
                            Text(text = unit.value)
                        },
                        onClick = {
                            onClicked(unit)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
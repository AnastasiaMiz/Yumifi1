package com.example.yumifi1.features.product_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R

@Composable
fun ProductDetailsView(
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.product_details_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally,
            )
        )
    }
}
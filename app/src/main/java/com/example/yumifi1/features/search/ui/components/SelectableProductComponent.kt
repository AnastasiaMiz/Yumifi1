package com.example.yumifi1.features.search.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.yumifi1.features.search.ui.data.SelectableData
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectableProductComponent(
    selectableData: SelectableData,
    modifier: Modifier = Modifier,
    onClicked: (SelectableData) -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = { onClicked(selectableData) }
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = selectableData.product.name
        )
        Checkbox(
            checked = selectableData.isSelected,
            onCheckedChange = {
                onClicked(selectableData)
            }
        )
    }
}
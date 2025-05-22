package com.example.yumifi1.features.recipe_details.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yumifi1.R

@Composable
fun RecipeDescriptionComponent(
    modifier: Modifier = Modifier,
    description: String,
    isEnabled: Boolean,
    onTextChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = description,
        placeholder = {
            Text(text = stringResource(id = R.string.recipe_description_label))
        },
        onValueChange = onTextChanged,
        readOnly = !isEnabled,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    )
}
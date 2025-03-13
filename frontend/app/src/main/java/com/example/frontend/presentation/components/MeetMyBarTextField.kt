package com.example.frontend.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.R
import com.example.frontend.ui.theme.SpritzColorLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetMyBarTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onTextFieldValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true  // Nouveau paramètre avec true par défaut
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.secondary,
        backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = {
                onTextFieldValueChange(it)
            },
            enabled = enabled,  // Utilisation du paramètre
            trailingIcon = {
                if (enabled) {  // N'afficher l'icône de suppression que si le champ est activé
                    IconButton(onClick = {
                        onTextFieldValueChange("")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(id = R.string.home_search),
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            },
            label = {
                Text(text = label)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.inversePrimary,
                unfocusedTextColor = if (enabled)
                    MaterialTheme.colorScheme.inversePrimary
                else
                    MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.6f),
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                disabledBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
                disabledLabelColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.secondary,
                disabledTextColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.6f),
            ),
            shape = RoundedCornerShape(7.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        )
    }
}

@Preview
@Composable
fun MeetMyBarTextFieldPreview() {
    MeetMyBarTextField(
        label = "Nom",
        value = "",
        onTextFieldValueChange = {},
    )
}

@Preview
@Composable
fun MeetMyBarTextFieldDisabledPreview() {
    MeetMyBarTextField(
        label = "Nom",
        value = "Valeur non modifiable",
        onTextFieldValueChange = {},
        enabled = false
    )
}
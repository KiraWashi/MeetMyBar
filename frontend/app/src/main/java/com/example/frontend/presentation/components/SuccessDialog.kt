package com.example.frontend.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.frontend.R

@Composable
fun SuccessDialog(
    successMessage: String,
    onDismissDialog: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            content = {
                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Success()
                        Text(
                            text = successMessage,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MeetMyBarButton(
                            text = stringResource(id = R.string.home_dismiss_dialog_error_button),
                            onClick = {
                                showDialog = false
                                onDismissDialog()
                            }
                        )
                    }
                }
            },
            onDismissRequest = {
                showDialog = false
                onDismissDialog()
            }
        )
    }
}
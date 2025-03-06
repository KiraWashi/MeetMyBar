package com.example.frontend.presentation.feature.addbar

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.R
import com.example.frontend.domain.model.ScheduleDayModel
import com.example.frontend.presentation.components.Error
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.components.MeetMyBarSecondaryButton
import com.example.frontend.presentation.components.MeetMyBarTextField
import com.example.frontend.presentation.components.SuccessDialog
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBarScreen(
    navHostController: NavHostController,
) {
    val viewModel = koinViewModel<AddBarViewModel>()
    val state = viewModel.addBarViewModelState.collectAsState()

    var showScheduleDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.inversePrimary,
                ),
                title = {
                    Text(text = stringResource(id = R.string.add_bar_title))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            MeetMyBarTextField(
                label = stringResource(id = R.string.add_bar_label_text_field_name),
                value = state.value.nameTextFieldValue,
                onTextFieldValueChange = {
                    viewModel.onNameTextFieldValueChange(newValue = it)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            MeetMyBarTextField(
                label = stringResource(id = R.string.add_bar_label_text_field_capacity),
                value = state.value.capacityTextFieldValue,
                onTextFieldValueChange = {
                    viewModel.onCapacityTextFieldValueChange(newValue = it)
                },
                keyboardType = KeyboardType.Number,
            )
            Spacer(modifier = Modifier.height(16.dp))
            MeetMyBarTextField(
                label = stringResource(id = R.string.add_bar_label_text_field_address),
                value = state.value.addressTextFieldValue,
                onTextFieldValueChange = {
                    viewModel.onAddressTextFieldValueChange(newValue = it)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            MeetMyBarTextField(
                label = stringResource(id = R.string.add_bar_label_text_field_postal_code),
                value = state.value.postalCodeTextFieldValue,
                onTextFieldValueChange = {
                    viewModel.onPostalCodeTextFieldValueChange(newValue = it)
                },
                keyboardType = KeyboardType.Number,
            )
            Spacer(modifier = Modifier.height(16.dp))
            MeetMyBarTextField(
                label = stringResource(id = R.string.add_bar_label_text_field_city),
                value = state.value.cityTextFieldValue,
                onTextFieldValueChange = {
                    viewModel.onCityTextFieldValueChange(newValue = it)
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            BarScheduleSection(planning = state.value.planning,
                onAddScheduleClick = { showScheduleDialog = true },
                getFrenshDay = { englishDay ->
                    viewModel.getFrenchDay(englishDay = englishDay)
                })

            Spacer(modifier = Modifier.height(24.dp))
            MeetMyBarButton(modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth(),
                text = stringResource(id = R.string.add_bar_label_button_label),
                onClick = {
                    viewModel.onClickAdd()
                })
            when (state.value.status) {
                AddBarScreenStatus.NO_STATUS -> {}
                AddBarScreenStatus.ERROR ->
                    ErrorDialog(onDismissDialog = { navHostController.navigateUp() })

                AddBarScreenStatus.SUCCES ->
                    SuccessDialog(
                        successMessage = stringResource(id = R.string.add_bar_success_message),
                        onDismissDialog = { navHostController.navigateUp() }
                    )
            }
        }
    }

    if (showScheduleDialog) {
        AddScheduleDialog(onDismiss = { showScheduleDialog = false },
            onAddSchedule = { opening, closing, day ->
                viewModel.addScheduleDay(
                    opening = opening, closing = closing, day = day
                )
                showScheduleDialog = false
            },
            getEnglishDay = { frenchDay ->
                viewModel.getEnglishDay(frenchDay = frenchDay)
            })
    }
}

@Composable
fun BarScheduleSection(
    planning: List<ScheduleDayModel>,
    onAddScheduleClick: () -> Unit,
    getFrenshDay: (String) -> String,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Horaires d'ouverture",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (planning.isNotEmpty()) {
                planning.forEach { scheduleDay ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${getFrenshDay(scheduleDay.day)}: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.inversePrimary
                        )
                        Text(
                            text = "${scheduleDay.opening} - ${scheduleDay.closing}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }
            } else {
                Text(
                    text = "Aucun horaire défini",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            MeetMyBarSecondaryButton(
                onClick = onAddScheduleClick, text = "Ajouter un horaire"
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun AddScheduleDialog(
    onDismiss: () -> Unit,
    onAddSchedule: (opening: String, closing: String, day: String) -> Unit,
    getEnglishDay: (String) -> String,
) {
    val daysList = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")

    var selectedDay by remember { mutableStateOf(daysList.first()) }
    var expandedDayDropdown by remember { mutableStateOf(false) }

    var showOpeningTimePicker by remember { mutableStateOf(false) }
    var showClosingTimePicker by remember { mutableStateOf(false) }
    var openingTime by remember { mutableStateOf("10:00") }
    var closingTime by remember { mutableStateOf("22:00") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Ajouter un horaire",
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedDayDropdown = true }
                    .padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Jour: $selectedDay", modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Sélectionner jour"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedDayDropdown,
                        onDismissRequest = { expandedDayDropdown = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        daysList.forEach { day ->
                            DropdownMenuItem(text = { Text(day) }, onClick = {
                                selectedDay = day
                                expandedDayDropdown = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Heure d'ouverture: ", modifier = Modifier.weight(1f)
                    )
                    Text(text = openingTime,
                        modifier = Modifier
                            .clickable { showOpeningTimePicker = true }
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.secondary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Heure de fermeture: ", modifier = Modifier.weight(1f)
                    )
                    Text(text = closingTime,
                        modifier = Modifier
                            .clickable { showClosingTimePicker = true }
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.secondary)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    MeetMyBarSecondaryButton(
                        onClick = onDismiss, text = "Annuler"
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    MeetMyBarButton(
                        onClick = {
                            onAddSchedule(openingTime, closingTime, getEnglishDay(selectedDay))
                        }, text = "Ajouter"
                    )
                }
            }
        }
    }

    TimePickerDialogCustom(showPicker = showOpeningTimePicker, onDismiss = {
        showOpeningTimePicker = false
    }, onTimeSelected = { selectedTime ->
        openingTime = selectedTime
    })

    TimePickerDialogCustom(showPicker = showClosingTimePicker, onDismiss = {
        showClosingTimePicker = false
    }, onTimeSelected = { selectedTime ->
        closingTime = selectedTime
    })
}

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogCustom(
    showPicker: Boolean, onDismiss: () -> Unit, onTimeSelected: (String) -> Unit
) {
    if (showPicker) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerState = rememberTimePickerState(
            initialHour = hour, initialMinute = minute, is24Hour = true
        )

        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                MeetMyBarButton(text = "OK", onClick = {
                    val selectedHour = timePickerState.hour
                    val selectedMinute = timePickerState.minute
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    onTimeSelected(formattedTime)
                    onDismiss()
                })
            },
            dismissButton = {
                MeetMyBarSecondaryButton(text = "Annuler", onClick = { onDismiss() })
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TimePicker(
                        state = timePickerState, colors = TimePickerDefaults.colors(
                            clockDialColor = MaterialTheme.colorScheme.primary,
                            clockDialSelectedContentColor = MaterialTheme.colorScheme.secondary,
                            clockDialUnselectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                            selectorColor = MaterialTheme.colorScheme.tertiary,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.secondary,
                            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.tertiary,
                            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    )
                }
            },
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AddBarScreenPreview() {
    AddBarScreen(
        navHostController = rememberNavController()
    )
}
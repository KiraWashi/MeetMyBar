package com.example.frontend.presentation.feature.modifybar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.R
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.components.MeetMyBarLoader
import com.example.frontend.presentation.components.MeetMyBarTextField
import com.example.frontend.presentation.components.SuccessDialog
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyBarScreen(
    navHostController: NavHostController,
    barId: Int
) {
    val viewModel = koinViewModel<ModifyBarViewModel>()
    val state = viewModel.modifyBarViewModelState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initScreen(barId = barId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.inversePrimary,
                ),
                title = {
                    Text(text = stringResource(id = R.string.modify_bar_title))
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
            when (state.value.status) {
                ModifyBarScreenStatus.SUCCESS, ModifyBarScreenStatus.ERROR, ModifyBarScreenStatus.NO_STATUS -> {
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
                    Spacer(modifier = Modifier.height(16.dp))
                    MeetMyBarButton(
                        text = stringResource(id = R.string.modify_bar_label_button_label)
                    ) {
                        viewModel.onClickModify()
                    }
                    when (state.value.status) {
                        ModifyBarScreenStatus.NO_STATUS, ModifyBarScreenStatus.LOADING -> {}
                        ModifyBarScreenStatus.ERROR ->
                            ErrorDialog(onDismissDialog = { navHostController.navigateUp() })

                        ModifyBarScreenStatus.SUCCESS ->
                            SuccessDialog(
                                successMessage = stringResource(id = R.string.add_bar_success_message),
                                onDismissDialog = { navHostController.navigateUp() }
                            )
                    }
                }

                ModifyBarScreenStatus.LOADING ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        MeetMyBarLoader(modifier = Modifier.align(Alignment.Center))
                    }
            }
        }
    }
}
package com.example.frontend.presentation.feature.modifybar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.model.BasicBarModel
import com.example.frontend.domain.model.ScheduleDayModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ModifyBarScreenStatus {
    LOADING,
    ERROR,
    SUCCESS,
    NO_STATUS
}

data class ModifyBarViewModelState(
    var nameTextFieldValue: String = "",
    var capacityTextFieldValue: String = "",
    var addressTextFieldValue: String = "",
    var cityTextFieldValue: String = "",
    var postalCodeTextFieldValue: String = "",
    var planning: List<ScheduleDayModel> = listOf(),
    var status: ModifyBarScreenStatus = ModifyBarScreenStatus.LOADING,
    val bar: BarModel? = null,
)

class ModifyBarViewModel (
    private val barRepository: BarRepositoryInterface,
): ViewModel() {

    private val _modifyBarViewModelState = MutableStateFlow(ModifyBarViewModelState())
    val modifyBarViewModelState = _modifyBarViewModelState.asStateFlow()

    fun initScreen(barId: Int) {
        viewModelScope.launch {
            barRepository.getBarById(barId = barId).collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _modifyBarViewModelState.update {
                        it.copy(
                            bar = resource.data,
                            status = ModifyBarScreenStatus.NO_STATUS,
                            nameTextFieldValue = resource.data?.name ?: "",
                            capacityTextFieldValue = resource.data?.capacity.toString(),
                            addressTextFieldValue = resource.data?.address ?: "",
                            cityTextFieldValue = resource.data?.city ?: "",
                            postalCodeTextFieldValue = resource.data?.postalCode.toString(),
                            planning = resource.data?.planning ?: listOf()
                        )
                    }
                }
            }
        }
    }

    fun onNameTextFieldValueChange(newValue: String) {
        _modifyBarViewModelState.update {
            it.copy(
                nameTextFieldValue = newValue
            )
        }
    }

    fun onCapacityTextFieldValueChange(newValue: String) {
        _modifyBarViewModelState.update {
            it.copy(
                capacityTextFieldValue = newValue
            )
        }
    }

    fun onAddressTextFieldValueChange(newValue: String) {
        _modifyBarViewModelState.update {
            it.copy(
                addressTextFieldValue = newValue
            )
        }
    }

    fun onCityTextFieldValueChange(newValue: String) {
        _modifyBarViewModelState.update {
            it.copy(
                cityTextFieldValue = newValue
            )
        }
    }

    fun onPostalCodeTextFieldValueChange(newValue: String) {
        _modifyBarViewModelState.update {
            it.copy(
                postalCodeTextFieldValue = newValue
            )
        }
    }

    fun onClickModify() {
        viewModelScope.launch {
            val newBar = BasicBarModel(
                id = _modifyBarViewModelState.value.bar?.id ?: -1,
                address = _modifyBarViewModelState.value.addressTextFieldValue,
                name = _modifyBarViewModelState.value.nameTextFieldValue,
                capacity = _modifyBarViewModelState.value.capacityTextFieldValue.toInt(),
                city = _modifyBarViewModelState.value.cityTextFieldValue,
                postalCode = _modifyBarViewModelState.value.postalCodeTextFieldValue,
            )
            barRepository.modifyBar(newBar).collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _modifyBarViewModelState.update {
                        it.copy(
                            status = ModifyBarScreenStatus.SUCCESS
                        )
                    }
                } else if (resource.status == Status.ERROR) {
                    _modifyBarViewModelState.update {
                        it.copy(
                            status = ModifyBarScreenStatus.ERROR
                        )
                    }
                }
            }
        }
    }
}
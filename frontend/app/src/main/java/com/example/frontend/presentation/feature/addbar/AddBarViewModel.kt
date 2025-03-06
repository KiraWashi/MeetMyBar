package com.example.frontend.presentation.feature.addbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.ScheduleDayModel
import com.example.frontend.domain.model.SimpleBarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AddBarScreenStatus {
    NO_STATUS,
    ERROR,
    SUCCES
}

data class AddBarViewModelState(
    var nameTextFieldValue: String = "",
    var capacityTextFieldValue: String = "",
    var addressTextFieldValue: String = "",
    var cityTextFieldValue: String = "",
    var postalCodeTextFieldValue: String = "",
    var planning: List<ScheduleDayModel> = listOf(),
    var status: AddBarScreenStatus = AddBarScreenStatus.NO_STATUS,
)

class AddBarViewModel(
    private val barRepository: BarRepositoryInterface,
): ViewModel() {

    private val _addBarViewModelState = MutableStateFlow(AddBarViewModelState())
    val addBarViewModelState = _addBarViewModelState.asStateFlow()

    fun onNameTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                nameTextFieldValue = newValue
            )
        }
    }

    fun onCapacityTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                capacityTextFieldValue = newValue
            )
        }
    }

    fun onAddressTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                addressTextFieldValue = newValue
            )
        }
    }

    fun onCityTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                cityTextFieldValue = newValue
            )
        }
    }

    fun onPostalCodeTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                postalCodeTextFieldValue = newValue
            )
        }
    }

    fun addScheduleDay(
        opening: String,
        closing: String,
        day: String
    ) {
        val newScheduleDay = ScheduleDayModel(
            opening = opening,
            closing = closing,
            day = day,
        )
        _addBarViewModelState.update {
            it.copy(
                planning = _addBarViewModelState.value.planning.plus(newScheduleDay)
            )
        }
    }

    fun getEnglishDay(frenchDay: String): String {
        return when (frenchDay.lowercase()) {
            "lundi" -> "Monday"
            "mardi" -> "Tuesday"
            "mercredi" -> "Wednesday"
            "jeudi" -> "Thursday"
            "vendredi" -> "Friday"
            "samedi" -> "Saturday"
            "dimanche" -> "Sunday"
            else -> "Invalid day"
        }
    }

    fun getFrenchDay(englishDay: String): String {
        return when (englishDay.lowercase()) {
            "monday" -> "Lundi"
            "tuesday" -> "Mardi"
            "wednesday" -> "Mercredi"
            "thursday" -> "Jeudi"
            "friday" -> "Vendredi"
            "saturday" -> "Samedi"
            "sunday" -> "Dimanche"
            else -> "Invalid day"
        }
    }

    fun onClickAdd() {
        viewModelScope.launch {
            val bar = SimpleBarModel(
                name = _addBarViewModelState.value.nameTextFieldValue,
                capacity = _addBarViewModelState.value.capacityTextFieldValue.toInt(),
                address = _addBarViewModelState.value.addressTextFieldValue,
                city = _addBarViewModelState.value.cityTextFieldValue,
                postalCode = _addBarViewModelState.value.postalCodeTextFieldValue,
                planning = _addBarViewModelState.value.planning
            )
            barRepository.addBars(bar = bar).collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _addBarViewModelState.update {
                        it.copy(
                            status = AddBarScreenStatus.SUCCES
                        )
                    }
                } else if (resource.status == Status.ERROR) {
                    _addBarViewModelState.update {
                        it.copy(
                            status = AddBarScreenStatus.ERROR
                        )
                    }
                }
            }
        }
    }
}
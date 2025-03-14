package com.example.frontend.presentation.feature.addbar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.R
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

data class FieldError(
    val hasError: Boolean = false,
    val errorMessage: String = ""
)

data class AddBarViewModelState(
    var nameTextFieldValue: String = "",
    var nameFieldError: FieldError = FieldError(),
    var capacityTextFieldValue: String = "",
    var capacityFieldError: FieldError = FieldError(),
    var addressTextFieldValue: String = "",
    var addressFieldError: FieldError = FieldError(),
    var cityTextFieldValue: String = "",
    var cityFieldError: FieldError = FieldError(),
    var postalCodeTextFieldValue: String = "",
    var postalCodeFieldError: FieldError = FieldError(),
    var planning: List<ScheduleDayModel> = listOf(),
    var planningError: FieldError = FieldError(),
    var status: AddBarScreenStatus = AddBarScreenStatus.NO_STATUS,
)

class AddBarViewModel(
    private val barRepository: BarRepositoryInterface,
    private val context: Context
): ViewModel() {

    private val _addBarViewModelState = MutableStateFlow(AddBarViewModelState())
    val addBarViewModelState = _addBarViewModelState.asStateFlow()

    fun onNameTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                nameTextFieldValue = newValue,
                nameFieldError = FieldError() // Réinitialiser l'erreur lorsque l'utilisateur modifie le champ
            )
        }
    }

    fun onCapacityTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                capacityTextFieldValue = newValue,
                capacityFieldError = FieldError() // Réinitialiser l'erreur
            )
        }
    }

    fun onAddressTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                addressTextFieldValue = newValue,
                addressFieldError = FieldError() // Réinitialiser l'erreur
            )
        }
    }

    fun onCityTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                cityTextFieldValue = newValue,
                cityFieldError = FieldError() // Réinitialiser l'erreur
            )
        }
    }

    fun onPostalCodeTextFieldValueChange(newValue: String) {
        _addBarViewModelState.update {
            it.copy(
                postalCodeTextFieldValue = newValue,
                postalCodeFieldError = FieldError() // Réinitialiser l'erreur
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
                planning = _addBarViewModelState.value.planning.plus(newScheduleDay),
                planningError = FieldError() // Réinitialiser l'erreur du planning
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

    /**
     * Vérifie si tous les champs obligatoires sont remplis correctement
     * @return true si tous les champs sont valides, false sinon
     */
    fun validateFields(): Boolean {
        var isValid = true

        // Validation du nom
        if (_addBarViewModelState.value.nameTextFieldValue.isBlank()) {
            _addBarViewModelState.update {
                it.copy(
                    nameFieldError = FieldError(
                        hasError = true,
                        errorMessage = context.getString(R.string.error_name_required)
                    )
                )
            }
            isValid = false
        }

        // Validation de la capacité
        if (_addBarViewModelState.value.capacityTextFieldValue.isBlank()) {
            _addBarViewModelState.update {
                it.copy(
                    capacityFieldError = FieldError(
                        hasError = true,
                        errorMessage = context.getString(R.string.error_capacity_required)
                    )
                )
            }
            isValid = false
        } else {
            try {
                val capacity = _addBarViewModelState.value.capacityTextFieldValue.toInt()
                if (capacity <= 0) {
                    _addBarViewModelState.update {
                        it.copy(
                            capacityFieldError = FieldError(
                                hasError = true,
                                errorMessage = context.getString(R.string.error_capacity_positive)
                            )
                        )
                    }
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                _addBarViewModelState.update {
                    it.copy(
                        capacityFieldError = FieldError(
                            hasError = true,
                            errorMessage = context.getString(R.string.error_capacity_number)
                        )
                    )
                }
                isValid = false
            }
        }

        // Validation de l'adresse
        if (_addBarViewModelState.value.addressTextFieldValue.isBlank()) {
            _addBarViewModelState.update {
                it.copy(
                    addressFieldError = FieldError(
                        hasError = true,
                        errorMessage = context.getString(R.string.error_address_required)
                    )
                )
            }
            isValid = false
        }

        // Validation de la ville
        if (_addBarViewModelState.value.cityTextFieldValue.isBlank()) {
            _addBarViewModelState.update {
                it.copy(
                    cityFieldError = FieldError(
                        hasError = true,
                        errorMessage = context.getString(R.string.error_city_required)
                    )
                )
            }
            isValid = false
        }

        // Validation du code postal
        if (_addBarViewModelState.value.postalCodeTextFieldValue.isBlank()) {
            _addBarViewModelState.update {
                it.copy(
                    postalCodeFieldError = FieldError(
                        hasError = true,
                        errorMessage = context.getString(R.string.error_postal_code_required)
                    )
                )
            }
            isValid = false
        } else {
            val postalCodeRegex = Regex("^\\d{5}$") // Regex pour valider un code postal français (5 chiffres)
            if (!postalCodeRegex.matches(_addBarViewModelState.value.postalCodeTextFieldValue)) {
                _addBarViewModelState.update {
                    it.copy(
                        postalCodeFieldError = FieldError(
                            hasError = true,
                            errorMessage = context.getString(R.string.error_postal_code_format)
                        )
                    )
                }
                isValid = false
            }
        }

        // Validation des horaires (optionnelle)
        if (_addBarViewModelState.value.planning.isEmpty()) {
            _addBarViewModelState.update {
                it.copy(
                    planningError = FieldError(
                        hasError = true,
                        errorMessage = context.getString(R.string.error_schedule_required)
                    )
                )
            }
            isValid = false
        }

        return isValid
    }

    fun onClickAdd() {
        // Vérifier si tous les champs sont valides avant d'ajouter le bar
        if (!validateFields()) {
            return
        }

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

    fun removeScheduleDay(scheduleDay: ScheduleDayModel) {
        val currentPlanning = _addBarViewModelState.value.planning.toMutableList()
        currentPlanning.remove(scheduleDay)

        _addBarViewModelState.update {
            it.copy(
                planning = currentPlanning
            )
        }
    }
}
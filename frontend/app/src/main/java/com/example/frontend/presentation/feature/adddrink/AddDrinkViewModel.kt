package com.example.frontend.presentation.feature.adddrink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Resource
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.DrinkTypeModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import com.example.frontend.presentation.feature.home.HomeStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddDrinkViewModelState(
    var drinks: List<DrinkTypeModel>? = emptyList(),
    val isLoading: Boolean = false,
    val priceTextField: String = "",
    val volumeTextField: String = "",
    val nameTextField: String = "",
    val alcoholDegreeTextField: String = "",
    val brandTextField: String = "",
    val selectedBeerColorTextField: String = "",
    val selectedDrink: DrinkTypeModel? = null,
    val addDrinkStatus: HomeStatus = HomeStatus.LOADING,
    val selectedTabIndex: Int = 0,
    val tabTitles: List<String> = listOf("Bière existante", "Nouvelle bière")
)

class AddDrinkViewModel(
    private val drinkRepository: DrinkRepositoryInterface,
) : ViewModel() {

    private val _addDrinkViewModelState = MutableStateFlow(AddDrinkViewModelState())
    val addDrinkViewModelState = _addDrinkViewModelState.asStateFlow()

    fun getDrinks() {
        viewModelScope.launch {
            _addDrinkViewModelState.update { it.copy(isLoading = true) }
            drinkRepository.getDrinks().collect { ret ->
                _addDrinkViewModelState.update {
                    it.copy(
                        drinks = ret.data
                    )
                }
            }
        }
    }

    fun onSelectedDrinkChange(newSelectedDrink: DrinkTypeModel) {
        _addDrinkViewModelState.update {
            it.copy(
                selectedDrink = newSelectedDrink
            )
        }
    }

    fun onNameTextFieldChange(newName: String) {
        _addDrinkViewModelState.update {
            it.copy(
                nameTextField = newName
            )
        }
    }

    fun onAlcoholDegreeTextFieldChange(newAlcoholDegree: String) {
        _addDrinkViewModelState.update {
            it.copy(
                alcoholDegreeTextField = newAlcoholDegree
            )
        }
    }

    fun onBrandTextFieldChange(newBrand: String) {
        _addDrinkViewModelState.update {
            it.copy(
                brandTextField = newBrand
            )
        }
    }

    fun onSelectedBeerColorTextFieldChange(newSelectedBeerColor: String) {
        _addDrinkViewModelState.update {
            it.copy(
                selectedBeerColorTextField = newSelectedBeerColor
            )
        }
    }

    fun onPriceTextFieldChange(newPrice: String) {
        _addDrinkViewModelState.update {
            it.copy(
                priceTextField = newPrice
            )
        }
    }

    fun onVolumeTextFieldChange(newVolume: String) {
        _addDrinkViewModelState.update {
            it.copy(
                volumeTextField = newVolume
            )
        }
    }

    fun onTabIndexChange(newIndex: Int) {
        _addDrinkViewModelState.update {
            it.copy(
                selectedTabIndex = newIndex
            )
        }
    }

    fun addDrinkToBar(barId: Int) {
        viewModelScope.launch {
            try {
                // Conversion des chaînes en Double avec gestion des erreurs
                val price = _addDrinkViewModelState.value.priceTextField.replace(",", ".").toDoubleOrNull()
                val volume = _addDrinkViewModelState.value.volumeTextField.replace(",", ".").toDoubleOrNull()

                if (price == null || volume == null) {
                    _addDrinkViewModelState.update {
                        it.copy(
                            addDrinkStatus = HomeStatus.ERROR,
                        )
                    }
                    return@launch
                }

                drinkRepository.addDrinkToBar(
                    idBar = barId,
                    idDrink = _addDrinkViewModelState.value.selectedDrink?.id ?: -1,
                    price = price,
                    volume = volume
                ).collect { resource ->
                    if (resource.status == Status.SUCCESS) {
                        _addDrinkViewModelState.update {
                            it.copy(
                                addDrinkStatus = HomeStatus.SUCCESS
                            )
                        }
                    } else if (resource.status == Status.ERROR) {
                        _addDrinkViewModelState.update {
                            it.copy(
                                addDrinkStatus = HomeStatus.ERROR
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _addDrinkViewModelState.update {
                    it.copy(
                        addDrinkStatus = HomeStatus.ERROR,
                    )
                }
            }
        }
    }

    fun createBeer(idBar: Int) {
        viewModelScope.launch {
            drinkRepository.createDrink(
                DrinkTypeModel(
                    id = 0,
                    name = _addDrinkViewModelState.value.nameTextField,
                    alcoholDegree = _addDrinkViewModelState.value.alcoholDegreeTextField,
                    brand = _addDrinkViewModelState.value.brandTextField,
                    type = _addDrinkViewModelState.value.selectedBeerColorTextField
                )
            ).collect { result ->
                when (result) {
                    is Resource.Error ->
                        _addDrinkViewModelState.update {
                            it.copy(
                                addDrinkStatus = HomeStatus.ERROR
                            )
                        }

                    is Resource.Success -> {
                        _addDrinkViewModelState.update {
                            it.copy(
                                addDrinkStatus = HomeStatus.SUCCESS,
                                selectedDrink = result.data
                            )
                        }
                        addDrinkToBar(barId = idBar)
                    }

                    is Resource.Loading ->
                        _addDrinkViewModelState.update {
                            it.copy(
                                addDrinkStatus = HomeStatus.LOADING
                            )
                        }
                }
            }
        }
    }
}
package com.example.frontend.presentation.biere

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.DrinkModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListBiereViewModelState(
    var drinks: List<DrinkModel>? = emptyList(),
)

class ListBiereViewModel(
    private val drinkRepository: DrinkRepositoryInterface,
): ViewModel() {

    private val _listeBiereViewModelState = MutableStateFlow(ListBiereViewModelState())
    val listeBiereViewModelState = _listeBiereViewModelState.asStateFlow()

    fun getDrinks() {
        viewModelScope.launch {
            drinkRepository.getDrinks().collect { ret ->
                _listeBiereViewModelState.update {
                    ListBiereViewModelState(
                        drinks = ret.data
                    )
                }
            }
        }
    }

    private val _createBiereViewModel = MutableStateFlow(AddBeerState())
    val createBiereViewModel = _createBiereViewModel.asStateFlow()

    fun createBeer(name: String, alcoholDegree: String, brand: String,type : String) {
        viewModelScope.launch {
            drinkRepository.createDrink(
                DrinkModel(
                    id = 0, // l'ID sera généré par l'API
                    name = name,
                    alcoholDegree = alcoholDegree.toDoubleOrNull() ?: 0.0,
                    brand = brand,
                    type = type // type fixe pour les bières
                )
            ).collect { result ->
                when (result) {
                    is Resource.Error -> _createBiereViewModel.value = AddBeerState(isLoading = true)
                    is Resource.Loading -> _createBiereViewModel.value = AddBeerState(success = true)
                    is Resource.Success ->  _createBiereViewModel.value = AddBeerState(
                        error = result.error?.message ?: "Une erreur est survenue"
                    )
                }
            }
        }
    }

}
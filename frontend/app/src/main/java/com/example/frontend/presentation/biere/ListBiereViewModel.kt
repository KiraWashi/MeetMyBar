package com.example.frontend.presentation.biere

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}
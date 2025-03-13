package com.example.frontend.presentation.feature.listebiere

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.DrinkOfBarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListBiereViewModelState(
    val drinks: List<DrinkOfBarModel> = emptyList(),
    val isLoading: Boolean = false
)

class ListBiereViewModel(
    private val barRepository: BarRepositoryInterface,
): ViewModel() {

    private val _listeBiereViewModelState = MutableStateFlow(ListBiereViewModelState())
    val listeBiereViewModelState = _listeBiereViewModelState.asStateFlow()

    fun getDrinks(barId: Int) {
        viewModelScope.launch {
            _listeBiereViewModelState.update { it.copy(isLoading = true) }

            barRepository.getBarsById(barId).collect { resource ->
                if (resource.status == Status.SUCCESS && resource.data != null) {
                    _listeBiereViewModelState.update {
                        it.copy(
                            drinks = resource.data.drinks,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}
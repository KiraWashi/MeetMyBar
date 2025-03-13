package com.example.frontend.presentation.feature.listebiere

import android.util.Log
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
            try {
                // Mettre à jour l'état pour indiquer le chargement
                _listeBiereViewModelState.update {
                    it.copy(
                        isLoading = true,
                       )
                }

                // Récupérer les données du repository
                barRepository.getBarsById(barId).collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data != null) {
                                val drinks = resource.data.drinks

                                _listeBiereViewModelState.update {
                                    it.copy(
                                        drinks = drinks,
                                        isLoading = false,)
                                }

                                // Vérifier l'état après mise à jour
                                val currentState = _listeBiereViewModelState.value
                            } else {
                                _listeBiereViewModelState.update {
                                    it.copy(
                                        isLoading = false,)
                                }
                            }
                        }
                        Status.ERROR -> {
                            _listeBiereViewModelState.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                        Status.LOADING -> {
                            _listeBiereViewModelState.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _listeBiereViewModelState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }
}
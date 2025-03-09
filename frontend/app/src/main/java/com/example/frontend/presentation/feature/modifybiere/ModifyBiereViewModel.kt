package com.example.frontend.presentation.feature.modifybiere

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.DrinkTypeModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ModifyBiereState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class ModifyBiereViewModel (
    private val drinkRepository: DrinkRepositoryInterface,
): ViewModel() {

    private val _modifyBiereState = MutableStateFlow(ModifyBiereState())
    val modifyBiereState = _modifyBiereState.asStateFlow()

    private val _deleteBiereState = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val deleteBiereState: StateFlow<Resource<Unit>> = _deleteBiereState

    private val _selectedBeer = MutableStateFlow<Resource<DrinkTypeModel?>>(Resource.Loading())
    val selectedBeer = _selectedBeer.asStateFlow()

    fun deleteBiere(id: Int) {
        viewModelScope.launch {
            _deleteBiereState.value = Resource.Loading()
            try {
                drinkRepository.deleteDrink(id).collect { result ->
                    when (result) {
                        is Resource.Success -> _deleteBiereState.value = Resource.Success(Unit)
                        is Resource.Error -> _deleteBiereState.value = Resource.Error(result.error ?: Throwable("Échec de la suppression"))
                        is Resource.Loading -> _deleteBiereState.value = Resource.Loading()
                    }
                }
            } catch (e: Exception) {
                _deleteBiereState.value = Resource.Error(Throwable("Erreur lors de la suppression"))
            }
        }
    }

    fun updateBeer(id: Int, name: String, alcoholDegree: String, brand: String, type: String) {
        viewModelScope.launch {
            _modifyBiereState.value = ModifyBiereState(isLoading = true) // Démarrer le chargement
            drinkRepository.updateDrink(
                DrinkTypeModel(
                    id = id,
                    name = name,
                    alcoholDegree = alcoholDegree,
                    brand = brand,
                    type = type
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> _modifyBiereState.value = ModifyBiereState(success = true) // ✅ Succès
                    is Resource.Error -> _modifyBiereState.value = ModifyBiereState(error = result.error?.message ?: "Une erreur est survenue") // ❌ Erreur
                    is Resource.Loading -> _modifyBiereState.value = ModifyBiereState(isLoading = true) // ⏳ En cours
                }
            }
        }
    }

    fun getDrink(id: Int) {
        viewModelScope.launch {
            drinkRepository.getDrink(id).collect { result ->
                _selectedBeer.value = result
            }
        }
    }
}
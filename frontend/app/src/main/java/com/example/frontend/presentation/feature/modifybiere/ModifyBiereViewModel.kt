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

data class DeleteBarDrinkLinkState(
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

    // Nouveaux états pour la suppression d'un lien bar-boisson
    private val _deleteBarDrinkLinkState = MutableStateFlow(DeleteBarDrinkLinkState())
    val deleteBarDrinkLinkState = _deleteBarDrinkLinkState.asStateFlow()

    private val _deleteBarDrinkResult = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val deleteBarDrinkResult: StateFlow<Resource<Unit>> = _deleteBarDrinkResult

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

    /**
     * Supprime un lien entre un bar et une boisson avec un volume spécifique
     */
    fun deleteBarDrinkLink(idBar: Int, idDrink: Int, volume: Int) {
        viewModelScope.launch {
            _deleteBarDrinkLinkState.value = DeleteBarDrinkLinkState(isLoading = true)
            _deleteBarDrinkResult.value = Resource.Loading()

            try {
                drinkRepository.deleteBarDrinkLink(idBar, idDrink, volume).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _deleteBarDrinkResult.value = Resource.Success(Unit)
                            _deleteBarDrinkLinkState.value = DeleteBarDrinkLinkState(success = true)
                        }
                        is Resource.Error -> {
                            _deleteBarDrinkResult.value = Resource.Error(result.error ?: Throwable("Échec de la suppression du lien"))
                            _deleteBarDrinkLinkState.value = DeleteBarDrinkLinkState(
                                error = result.error?.message ?: "Une erreur est survenue lors de la suppression du lien"
                            )
                        }
                        is Resource.Loading -> {
                            _deleteBarDrinkResult.value = Resource.Loading()
                            _deleteBarDrinkLinkState.value = DeleteBarDrinkLinkState(isLoading = true)
                        }
                    }
                }
            } catch (e: Exception) {
                _deleteBarDrinkResult.value = Resource.Error(Throwable("Erreur lors de la suppression du lien"))
                _deleteBarDrinkLinkState.value = DeleteBarDrinkLinkState(
                    error = e.message ?: "Une erreur inattendue est survenue"
                )
            }
        }
    }

    /**
     * Réinitialise l'état de suppression du lien bar-boisson
     */
    fun resetDeleteBarDrinkLinkState() {
        _deleteBarDrinkLinkState.value = DeleteBarDrinkLinkState()
        _deleteBarDrinkResult.value = Resource.Loading()
    }

    fun updateDrinkPrice(idBar: Int, idDrink: Int, volume: String, newPrice: String) {
        viewModelScope.launch {
            try {
                // Conversion de String en Float
                val volumeFloat = volume.toFloatOrNull() ?: throw IllegalArgumentException("Volume invalide")
                val priceFloat = newPrice.toFloatOrNull() ?: throw IllegalArgumentException("Prix invalide")

                drinkRepository.updateDrinkPrice(idBar, idDrink, volumeFloat, priceFloat).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _modifyBiereState.value = ModifyBiereState(success = true)
                        }
                        is Resource.Error -> {
                            _modifyBiereState.value = ModifyBiereState(error = result.error?.message ?: "Une erreur est survenue")
                        }
                        else -> {}
                    }
                }
            } catch (e: IllegalArgumentException) {
                _modifyBiereState.value = ModifyBiereState(error = e.message ?: "Valeur invalide")
            }
        }
    }
}
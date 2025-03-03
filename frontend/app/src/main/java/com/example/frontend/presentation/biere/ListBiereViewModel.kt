package com.example.frontend.presentation.biere

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.DrinkModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListBiereViewModelState(
    var drinks: List<DrinkModel>? = emptyList(),
    val isLoading: Boolean = false
)

class ListBiereViewModel(
    private val drinkRepository: DrinkRepositoryInterface,
): ViewModel() {

    private val _listeBiereViewModelState = MutableStateFlow(ListBiereViewModelState())
    val listeBiereViewModelState = _listeBiereViewModelState.asStateFlow()

    fun getDrinks() {
        viewModelScope.launch {
            _listeBiereViewModelState.update { it.copy(isLoading = true) }
            drinkRepository.getDrinks().collect { ret ->
                _listeBiereViewModelState.update {
                    ListBiereViewModelState(
                        drinks = ret.data
                    )
                }
            }
        }
    }
    private val _selectedBeer = MutableStateFlow<Resource<DrinkModel?>>(Resource.Loading())
    val selectedBeer = _selectedBeer.asStateFlow()

    fun getDrink(id: Int) {
        viewModelScope.launch {
            drinkRepository.getDrink(id).collect { result ->
                _selectedBeer.value = result
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
                    alcoholDegree = alcoholDegree,
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

    fun updateBeer(id: Int, name: String, alcoholDegree: String, brand: String, type: String) {
        viewModelScope.launch {
            _createBiereViewModel.value = AddBeerState(isLoading = true) // Démarrer le chargement
            drinkRepository.updateDrink(
                DrinkModel(
                    id = id,
                    name = name,
                    alcoholDegree = alcoholDegree,
                    brand = brand,
                    type = type
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> _createBiereViewModel.value = AddBeerState(success = true) // ✅ Succès
                    is Resource.Error -> _createBiereViewModel.value = AddBeerState(error = result.error?.message ?: "Une erreur est survenue") // ❌ Erreur
                    is Resource.Loading -> _createBiereViewModel.value = AddBeerState(isLoading = true) // ⏳ En cours
                }
            }
        }
    }

    private val _deleteBiereState = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val deleteBiereState: StateFlow<Resource<Unit>> = _deleteBiereState


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

}
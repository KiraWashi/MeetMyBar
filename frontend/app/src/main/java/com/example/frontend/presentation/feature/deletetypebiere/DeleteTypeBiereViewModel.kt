package com.example.frontend.presentation.feature.deletetypebiere

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.DrinkTypeModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class DeleteTypeBiereScreenStatus {
    LOADING,
    ERROR,
    SUCCESS,
    NO_STATUS
}

data class DeleteTypeBiereViewModelState(
    var status: DeleteTypeBiereScreenStatus = DeleteTypeBiereScreenStatus.LOADING,
    var bieresType: List<DrinkTypeModel> = listOf(),
)

class DeleteTypeBiereViewModel (
    private val drinkRepository: DrinkRepositoryInterface,
): ViewModel() {

    private val _deleteTypeBiereViewModelState = MutableStateFlow(DeleteTypeBiereViewModelState())
    val deleteTypeBiereViewModelState = _deleteTypeBiereViewModelState.asStateFlow()

    fun initScreen() {
        viewModelScope.launch {
            drinkRepository.getDrinks().collect { resource ->
                when(resource.status) {
                    Status.SUCCESS ->
                        _deleteTypeBiereViewModelState.update {
                            it.copy(
                                status = DeleteTypeBiereScreenStatus.NO_STATUS,
                                bieresType = resource.data ?: listOf()
                            )
                        }
                    Status.ERROR ->
                        _deleteTypeBiereViewModelState.update {
                            it.copy(
                                status = DeleteTypeBiereScreenStatus.ERROR
                            )
                        }
                    Status.LOADING ->
                        _deleteTypeBiereViewModelState.update {
                            it.copy(
                                status = DeleteTypeBiereScreenStatus.LOADING
                            )
                        }
                }
            }
        }
    }

    fun deleteBarById(barId: Int) {
        viewModelScope.launch {
            drinkRepository.deleteDrink(id = barId).collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _deleteTypeBiereViewModelState.update {
                        it.copy(
                            status = DeleteTypeBiereScreenStatus.SUCCESS
                        )
                    }
                } else if (resource.status == Status.ERROR) {
                    _deleteTypeBiereViewModelState.update {
                        it.copy(
                            status = DeleteTypeBiereScreenStatus.ERROR
                        )
                    }
                }
            }
        }
    }
}
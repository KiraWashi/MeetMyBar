package com.example.frontend.presentation.feature.deletebar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import com.example.frontend.presentation.feature.home.HomeStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DeleteBarViewModelState(
    var bars: List<BarModel>? = emptyList(),
    var selectedBars: Set<Int> = emptySet(),
    var deleteBarScreenStatus: HomeStatus = HomeStatus.LOADING,
    var showErrorDialog: Boolean = false,
    var successMessage: String? = null,
)

class DeleteBarViewModel(
    private val barRepository: BarRepositoryInterface,
): ViewModel() {
    private val _deleteBarViewModelState = MutableStateFlow(DeleteBarViewModelState())
    val deleteBarViewModelState = _deleteBarViewModelState.asStateFlow()

    init {
        getBars()
    }

    fun getBars() {
        viewModelScope.launch {
            _deleteBarViewModelState.update { it.copy(deleteBarScreenStatus = HomeStatus.LOADING) }
            barRepository.getBars().collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _deleteBarViewModelState.update {
                        it.copy(
                            bars = resource.data,
                            deleteBarScreenStatus = HomeStatus.SUCCESS
                        )
                    }
                } else if (resource.status == Status.ERROR) {
                    _deleteBarViewModelState.update {
                        it.copy(
                            deleteBarScreenStatus = HomeStatus.ERROR,
                            showErrorDialog = true
                        )
                    }
                }
            }
        }
    }

    fun toggleBarSelection(barId: Int) {
        _deleteBarViewModelState.update {
            val newSelection = it.selectedBars.toMutableSet()
            if (newSelection.contains(barId)) {
                newSelection.remove(barId)
            } else {
                newSelection.add(barId)
            }
            it.copy(selectedBars = newSelection)
        }
    }

    fun deleteSelectedBars() {
        val barsToDelete = _deleteBarViewModelState.value.selectedBars
        if (barsToDelete.isEmpty()) return

        viewModelScope.launch {
            _deleteBarViewModelState.update { it.copy(deleteBarScreenStatus = HomeStatus.LOADING) }

            var successCount = 0
            var errorCount = 0

            for (barId in barsToDelete) {
                barRepository.deleteBarById(barId).collect { resource ->
                    if (resource.status == Status.SUCCESS) {
                        successCount++
                    } else if (resource.status == Status.ERROR) {
                        errorCount++
                    }
                }
            }

            // Mettre à jour l'état après toutes les suppressions
            if (errorCount == 0) {
                _deleteBarViewModelState.update {
                    it.copy(
                        selectedBars = emptySet(),
                        successMessage = "Suppression réussie de $successCount bar(s)",
                        deleteBarScreenStatus = HomeStatus.SUCCESS
                    )
                }
                // Recharger la liste des bars après suppression
                getBars()
            } else {
                _deleteBarViewModelState.update {
                    it.copy(
                        deleteBarScreenStatus = HomeStatus.ERROR,
                        showErrorDialog = true,
                        successMessage = if (successCount > 0) "Suppression partielle: $successCount réussi(s), $errorCount échoué(s)" else null
                    )
                }
            }
        }
    }

    fun deleteBarById(barId: Int) {
        viewModelScope.launch {
            _deleteBarViewModelState.update { it.copy(deleteBarScreenStatus = HomeStatus.LOADING) }
            barRepository.deleteBarById(barId).collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _deleteBarViewModelState.update {
                        it.copy(
                            successMessage = "Bar supprimé avec succès",
                            deleteBarScreenStatus = HomeStatus.SUCCESS
                        )
                    }
                    // Recharger la liste des bars après suppression
                    getBars()
                } else if (resource.status == Status.ERROR) {
                    _deleteBarViewModelState.update {
                        it.copy(
                            deleteBarScreenStatus = HomeStatus.ERROR,
                            showErrorDialog = true
                        )
                    }
                }
            }
        }
    }

    fun clearSuccessMessage() {
        _deleteBarViewModelState.update {
            it.copy(successMessage = null)
        }
    }

    fun hideDialog() {
        _deleteBarViewModelState.update {
            it.copy(showErrorDialog = false)
        }
    }
}
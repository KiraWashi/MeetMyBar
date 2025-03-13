package com.example.frontend.presentation.feature.bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BarScreenViewModelState (
    var bar: BarModel? = null,
)

class BarScreenViewModel(
    private val barRepository: BarRepositoryInterface,
) : ViewModel() {
    private val _barScreenViewModelState = MutableStateFlow(BarScreenViewModelState())
    val barScreenViewModelState = _barScreenViewModelState.asStateFlow()

    fun getBarsById(barId: Int) {
        viewModelScope.launch {
            barRepository.getBarById(barId = barId).collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _barScreenViewModelState.update {
                        it.copy(
                            bar = resource.data
                        )
                    }
                }
            }
        }
    }
}
package com.example.frontend.presentation.feature.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import com.example.frontend.domain.repository.PreferencesRepositoryInterface
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed class LocalisationState {
    object Loading : LocalisationState()
    object Granted : LocalisationState()
    object Denied : LocalisationState()
    object RequiresPermission : LocalisationState()
}

enum class HomeStatus {
    LOADING,
    ERROR,
    SUCCESS,
}

data class HomeViewModelState(
    var userLocation: LatLng? = null,
    var locationState: LocalisationState = LocalisationState.Loading,
    var bars: List<BarModel>? = emptyList(),
    var selectedBar: BarModel? = null,
    var barsSearch: List<BarModel>? = emptyList(),
    var homeStatus: HomeStatus = HomeStatus.LOADING,
    var showDialog: Boolean = false,
    var isAppInDarkTheme: Boolean = false,
)

class HomeViewModel(
    private val barRepository: BarRepositoryInterface,
    private val preferencesRepository: PreferencesRepositoryInterface
) : ViewModel() {

    private val _homeViewModelState = MutableStateFlow(HomeViewModelState())
    val homeViewModelState = _homeViewModelState.asStateFlow()

    fun updatePermissionState(state: LocalisationState) {
        _homeViewModelState.update {
            it.copy(
                locationState = state
            )
        }
    }

    fun getCurrentLocation(context: Context) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            locationClient.lastLocation.addOnSuccessListener { location ->
                _homeViewModelState.update {
                    it.copy(
                        userLocation = LatLng(location.latitude, location.longitude)
                    )
                }
            }
        } catch (e: SecurityException) {
            _homeViewModelState.update {
                it.copy(
                    locationState = LocalisationState.RequiresPermission
                )
            }
        }
    }

    fun onSearchChange(
        search: String,
    ) {
        _homeViewModelState.update {
            it.copy(
                barsSearch = it.bars?.filter { bar ->
                    bar.name.contains(search, ignoreCase = true)
                }
            )
        }
    }

    fun getBars() {
        viewModelScope.launch {
            _homeViewModelState.update { it.copy(homeStatus = HomeStatus.LOADING) }
            barRepository.getBars().collect { resource ->
                if (resource.status == Status.SUCCESS) {
                    _homeViewModelState.update {
                        it.copy(
                            bars = resource.data,
                            barsSearch = resource.data,
                            homeStatus = HomeStatus.SUCCESS
                        )
                    }
                } else if (resource.status == Status.ERROR) {
                    _homeViewModelState.update {
                        it.copy(
                            homeStatus = HomeStatus.ERROR,
                            showDialog = true,
                        )
                    }
                }
            }
        }
    }

    fun hideDialog() {
        _homeViewModelState.update {
            it.copy(
                showDialog = false
            )
        }
    }

    fun onClickBar(bar: BarModel?) {
        _homeViewModelState.update {
            it.copy(
                selectedBar = bar
            )
        }
    }

    fun isOpen(bar: BarModel): Boolean {
        val today = LocalDate.now().dayOfWeek.name.lowercase()
            .replaceFirstChar { it.titlecase(Locale.ROOT) }

        val todayPlanning = bar.planning.find { it.day == today } ?: return false

        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val openingTime = LocalTime.parse(todayPlanning.opening, timeFormatter)
        var closingTime = LocalTime.parse(todayPlanning.closing, timeFormatter)

        if (closingTime.isBefore(openingTime)) {
            closingTime = closingTime.plusHours(24)
        }

        val now = LocalTime.now()

        return now in openingTime..closingTime
    }


    fun isAppInDarkTheme() {
        viewModelScope.launch {
            preferencesRepository.getThemeMode().collect { themePreference ->
               _homeViewModelState.update {
                   it.copy(
                       isAppInDarkTheme = true
                   )
               }
            }
        }
    }
}
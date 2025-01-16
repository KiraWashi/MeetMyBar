package com.example.frontend.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class LocalisationState {
    object Loading : LocalisationState()
    object Granted : LocalisationState()
    object Denied : LocalisationState()
    object RequiresPermission : LocalisationState()
}

class HomeViewModel: ViewModel() {
    private val _localisationState = MutableStateFlow<LocalisationState>(LocalisationState.Loading)
    val locationPermissionState = _localisationState.asStateFlow()

    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation = _userLocation.asStateFlow()

    private val fusedLocationClient: FusedLocationProviderClient? = null

    fun updatePermissionState(state: LocalisationState) {
        _localisationState.value = state
    }

    fun getCurrentLocation(context: Context) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    _userLocation.value = LatLng(it.latitude, it.longitude)
                }
            }
        } catch (e: SecurityException) {
            _localisationState.value = LocalisationState.RequiresPermission
        }
    }
}
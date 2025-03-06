package com.example.frontend.presentation.feature.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.repository.photo.BarPhoto
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class PhotoViewModelState(
    val photo: Bitmap? = null,
    val isUploading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val errorMessage: String? = null
)
class PhotoViewModel(
    private val photoRepository: PhotoRepositoryInterface,
) : ViewModel() {

    private val _photos = MutableStateFlow<List<BarPhoto>>(emptyList())
    val photos: StateFlow<List<BarPhoto>> = _photos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _photoViewModelState = MutableStateFlow(PhotoViewModelState())
    val photoViewModelState = _photoViewModelState.asStateFlow()

    // Map pour stocker l'état de chaque photo par ID
    private val _photoStates = mutableMapOf<Int, MutableStateFlow<PhotoState>>()

    data class PhotoState(
        val photo: Bitmap? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    // Fonction pour obtenir ou créer un StateFlow pour une photo spécifique
    fun getPhotoState(photoId: Int): StateFlow<PhotoState> {
        return _photoStates.getOrPut(photoId) {
            MutableStateFlow(PhotoState(isLoading = false))
        }
    }

    // Fonction pour charger une photo spécifique
    fun loadPhoto(photoId: Int) {
        // Vérifier si la photo est déjà chargée
        val currentState = _photoStates[photoId]?.value
        if (currentState?.photo != null || currentState?.isLoading == true) {
            return // Éviter de recharger une photo déjà chargée ou en cours de chargement
        }

        // Créer ou mettre à jour l'état pour indiquer le chargement
        _photoStates.getOrPut(photoId) {
            MutableStateFlow(PhotoState(isLoading = true))
        }.value = PhotoState(isLoading = true)

        viewModelScope.launch {
            photoRepository.getPhotoById(photoId).collect { resource ->
                val stateFlow = _photoStates.getOrPut(photoId) {
                    MutableStateFlow(PhotoState())
                }

                when (resource.status) {
                    Status.LOADING -> {
                        // État déjà mis à jour
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { byteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            stateFlow.value = PhotoState(photo = bitmap, isLoading = false)
                        }
                    }
                    Status.ERROR -> {
                        stateFlow.value = PhotoState(
                            isLoading = false,
                            errorMessage = resource.error?.message
                        )
                    }
                }
            }
        }
    }

    fun getPhotosByBar(barId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Appel du repository pour récupérer les photos
                val photosList = photoRepository.getPhotosByBar(barId)
                // Mise à jour de l'état avec les photos récupérées
                _photos.value = photosList
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Le reste du code reste inchangé
    fun getPhotoById(id: Int) {
        viewModelScope.launch {
            photoRepository.getPhotoById(id).collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        resource.data?.let { byteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            _photoViewModelState.update {
                                it.copy(photo = bitmap)
                            }
                        }
                    }
                    Status.ERROR -> {
                        _photoViewModelState.update {
                            it.copy(errorMessage = resource.error?.message)
                        }
                    }
                }
            }
        }
    }

    fun resetUploadState() {
        _photoViewModelState.update {
            it.copy(uploadSuccess = false, errorMessage = null)
        }
    }
}
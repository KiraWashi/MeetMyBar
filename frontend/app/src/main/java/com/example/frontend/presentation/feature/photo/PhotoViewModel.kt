package com.example.frontend.presentation.feature.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Status
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _photoViewModelState = MutableStateFlow(PhotoViewModelState())
    val photoViewModelState = _photoViewModelState.asStateFlow()

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
                            it.copy(errorMessage = resource.message)
                        }
                    }
                }
            }
        }
    }

    fun postPhoto(uri: Uri, context: Context, mainPhoto: Boolean, description: String) {
        _photoViewModelState.update {
            it.copy(isUploading = true, uploadSuccess = false, errorMessage = null)
        }

        viewModelScope.launch {
            photoRepository.postPhoto(uri, context, mainPhoto, description).collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        // État déjà mis à jour
                    }
                    Status.SUCCESS -> {
                        _photoViewModelState.update {
                            it.copy(isUploading = false, uploadSuccess = true)
                        }
                    }
                    Status.ERROR -> {
                        _photoViewModelState.update {
                            it.copy(
                                isUploading = false,
                                errorMessage = resource.message ?: "Erreur lors de l'upload"
                            )
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
package com.example.frontend.presentation.feature.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PhotoViewModelState(
    val photo: Bitmap? = null,
)

class PhotoViewModel(
    private val photoRepository: PhotoRepositoryInterface,
) : ViewModel() {

    private val _photoViewModelState = MutableStateFlow(PhotoViewModelState())
    val photoViewModelState = _photoViewModelState.asStateFlow()

    fun getPhotoById(id: Int) {
        viewModelScope.launch {
            photoRepository.getPhotoById(id = id).collect { photoModel ->
                photoModel.data?.let { photo ->
                    _photoViewModelState.update {
                        it.copy(photo = decodeBase64ToBitmap(photo.imageData))
                    }
                }
            }
        }
    }

    private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            // Décoder le Base64 en un tableau de bytes
            val imageData = Base64.decode(base64String, Base64.DEFAULT)

            // Convertir en Bitmap directement
            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
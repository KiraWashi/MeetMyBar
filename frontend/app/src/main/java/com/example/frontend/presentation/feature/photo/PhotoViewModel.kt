package com.example.frontend.presentation.feature.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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
            photoRepository.getPhotoById(id).collect { resource ->
                resource.data?.let { byteArray ->
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    _photoViewModelState.update {
                        it.copy(photo = bitmap)
                    }
                }
            }
        }
    }

    fun postPhoto(uri: Uri, context: Context, mainPhoto: Boolean, description: String) {
        viewModelScope.launch {
            photoRepository.postPhoto(uri, context, mainPhoto, description).collect { resource ->
                when (resource) {
                    is Resource.Loading -> { /* gérer loading */ }
                    is Resource.Success -> println("Upload success: ${resource.data}")
                    is Resource.Error -> println("Upload failed: ${resource.data}")
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

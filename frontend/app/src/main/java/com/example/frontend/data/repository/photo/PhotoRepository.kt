package com.example.frontend.data.repository.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.PhotoModel
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.core.component.KoinComponent
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import com.example.frontend.data.utils.Resource.*

class PhotoRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
) : PhotoRepositoryInterface, KoinComponent {

    override suspend fun getPhotoById(id: Int): Flow<Resource<ByteArray>> = flow {
        emit(Resource.Loading())
        val byteArray = meetMyBarAPI.getPhotoById(id) // Récupère directement les données binaires
        emit(Resource.Success(byteArray))
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPhoto(
        uri: Uri,
        context: Context,
        mainPhoto: Boolean,
        description: String
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val imageFile = uriToFile(uri, context)
            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
            val response = meetMyBarAPI.postPhoto(imageFile, mimeType, mainPhoto, description)
            emit(Resource.Success(response.content.toString()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Erreur lors de l'upload"))
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: throw IOException("Cannot open input stream")

        // Créer un fichier temporaire
        val tempFile = File(context.cacheDir, "upload_image.jpg")
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return tempFile
    }
}
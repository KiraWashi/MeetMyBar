package com.example.frontend.data.repository.photo

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import android.util.Base64
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BarPhoto(
    val description: String,
    val id: Int,
)

class PhotoRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : PhotoRepositoryInterface, KoinComponent {

    private val imageCache: MutableMap<String, ByteArray> = mutableMapOf()

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
    override suspend fun getPhotosByBar(barId: Int): List<BarPhoto> {
        return try {
            // Appel de l'API pour obtenir les photos du bar
            val photos = meetMyBarAPI.getPhotosByBar(barId)

            // Optionnel : Si tu veux effectuer un traitement sur les données avant de les retourner
            // Par exemple, filtrer ou transformer les données.
            photos
        } catch (exception: Exception) {
            // Optionnel : Gestion d'erreurs, tu peux logger ou renvoyer une valeur par défaut
            Log.e("BarRepository", "Erreur lors de la récupération des photos : ${exception.message}")
            emptyList()
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
package com.example.frontend.data.repository.photo

import android.util.Log
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

@Serializable
data class BarPhoto(
    val description: String,
    val id: Int,
)

class PhotoRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
) : PhotoRepositoryInterface, KoinComponent {

    override suspend fun getPhotoById(id: Int): Flow<Resource<ByteArray>> = flow {
        emit(Resource.Loading())
        val byteArray = meetMyBarAPI.getPhotoById(id) // Récupère directement les données binaires
        emit(Resource.Success(byteArray))
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
}
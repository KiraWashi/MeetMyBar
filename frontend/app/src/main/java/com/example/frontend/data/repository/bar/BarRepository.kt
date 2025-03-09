package com.example.frontend.data.repository.bar

import android.location.Geocoder
import android.util.Log
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.model.SimpleBarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import com.google.android.gms.maps.model.LatLng
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import android.content.Context

class BarRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
    private val context: Context
) : BarRepositoryInterface {
    override suspend fun getBars(): Flow<Resource<List<BarModel>?>> = flow {
        emit(Resource.Loading())
        try {
            val barsModel = meetMyBarAPI.getBars().map { barVo ->
                val barModel = barVo.toModel()

                val coordinates = getLatLngFromAddress(barModel)

                barModel.copy(
                    latitude = coordinates?.latitude,
                    longitude = coordinates?.longitude
                )
            }
            emit(Resource.Success(barsModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun addBars(bar: SimpleBarModel): Flow<Resource<HttpResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = meetMyBarAPI.postBar(bar = bar.toVo())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e))
            Log.e("Erreur Appel Api", e.toString())
        }
    }

    override suspend fun getBarsById(barId: Int): Flow<Resource<BarModel>> = flow {
        emit(Resource.Loading())
        try {
            val barModel = meetMyBarAPI.getBarById(barId).toModel()

            val coordinates = getLatLngFromAddress(barModel)

             barModel.copy(
                latitude = coordinates?.latitude,
                longitude = coordinates?.longitude
            )

            emit(Resource.Success(barModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun deleteBarById(barId: Int): Flow<Resource<HttpResponse>> = flow {
        emit(Resource.Loading())
        try {
            val reponse = meetMyBarAPI.deleteBarById(barId)
            emit(Resource.Success(reponse))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    private fun getLatLngFromAddress(barModel: BarModel): LatLng? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val fullAddress = "${barModel.address}, ${barModel.city}, ${barModel.postalCode}"

            @Suppress("DEPRECATION") // Pour compatibilité avec les anciennes versions d'Android
            val addresses = geocoder.getFromLocationName(fullAddress, 1)

            if (!addresses.isNullOrEmpty()) {
                LatLng(addresses[0].latitude, addresses[0].longitude)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Geocoding", "Erreur lors de la conversion de l'adresse", e)
            null
        }
    }
}
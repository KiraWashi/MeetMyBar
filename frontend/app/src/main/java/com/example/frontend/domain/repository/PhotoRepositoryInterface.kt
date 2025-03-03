package com.example.frontend.domain.repository

import android.content.Context
import android.net.Uri
import com.example.frontend.data.repository.photo.BarPhoto
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.InputStream

interface PhotoRepositoryInterface {
    suspend fun getPhotoById(id: Int): Flow<Resource<ByteArray>>
    suspend fun postPhoto(
        uri: Uri,
        context: Context,
        mainPhoto: Boolean,
        description: String
    ): Flow<Resource<String>>

    suspend fun getPhotosByBar(barId: Int): List<BarPhoto>
}
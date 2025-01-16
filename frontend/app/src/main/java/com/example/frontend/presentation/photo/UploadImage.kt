package com.example.frontend.presentation.photo

import android.content.Context
import android.net.Uri
import com.google.android.gms.common.api.Response
import com.google.firebase.appdistribution.gradle.models.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
/*
// Service Retrofit
interface ApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>
}*/

//CLASSE D'EXEMPLE

// Fonction d'upload
suspend fun uploadImage(context: Context, uri: Uri) {
    val stream = context.contentResolver.openInputStream(uri)
    val request = stream?.let { inputStream ->
        val requestFile = inputStream.readBytes()
            .toRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData(
            "image",
            "image.jpg",
            requestFile
        )
    }

    request?.let {
        try {
            //val response = apiService.uploadImage(it)
            // Gérer la réponse
        } catch (e: Exception) {
            // Gérer l'erreur
        }
    }
}
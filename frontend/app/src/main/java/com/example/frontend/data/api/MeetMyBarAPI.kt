package com.example.frontend.data.api

import com.example.frontend.data.vo.DrinkVo
import com.example.frontend.data.vo.PhotoVo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.headers
import org.koin.core.component.KoinComponent
import java.io.File


class MeetMyBarAPI(
    private val client: HttpClient,
    private val baseUrl: String = "http://leop.letareau.fr:8080",
) : KoinComponent {

    suspend fun getDrinks(): List<DrinkVo> {
        return client.get("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
        }.body<List<DrinkVo>>()
    }

    suspend fun getPhotoById(id: Int): ByteArray {
        return client.get("$baseUrl/photos/download/$id") {
            contentType(ContentType.Image.JPEG)
        }.body<ByteArray>()
    }

    suspend fun postPhoto(
        file: File,
        mimeType: String,
        mainPhoto: Boolean,
        description: String
    ): HttpResponse {
        val byteArray = file.readBytes()

        val formData = MultiPartFormDataContent(
            formData {
                append("description", description)
                append("main_photo", if(mainPhoto) "true" else "false")
                append("image", byteArray, Headers.build {
                    append(HttpHeaders.ContentType, mimeType)
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"image\"; filename=\"image.jpg\"")
                })
            }
        )

        return client.post("$baseUrl/photos") {
            setBody(formData)
        }
    }

}

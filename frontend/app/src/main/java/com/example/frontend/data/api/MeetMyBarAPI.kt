package com.example.frontend.data.api

import com.example.frontend.data.vo.DrinkVo
import com.example.frontend.data.vo.PhotoVo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent


class MeetMyBarAPI(
    private val client: HttpClient,
    private val baseUrl: String = " http://leop.letareau.fr:8080",
) : KoinComponent {

    suspend fun getDrinks(): List<DrinkVo> {
        return client.get("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
        }.body<List<DrinkVo>>()
    }

    suspend fun getPhotoById(id : Int): PhotoVo {
        return client.get("$baseUrl/photos/$id") {
            contentType(ContentType.Application.Json)
        }.body<PhotoVo>()
    }
}

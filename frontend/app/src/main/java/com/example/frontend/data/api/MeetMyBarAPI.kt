package com.example.frontend.data.api

import android.util.Log
import com.example.frontend.data.repository.photo.BarPhoto
import com.example.frontend.data.vo.BarVo
import com.example.frontend.data.vo.BasicBarVo
import com.example.frontend.data.vo.DrinkTypeVo
import com.example.frontend.data.vo.SimpleBarVo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent


class MeetMyBarAPI(
    private val client: HttpClient,
    private val baseUrl: String = " http://leop.letareau.fr:8080",
) : KoinComponent {

    //GET - Toutes les boissons de la base
    suspend fun getDrinks(): List<DrinkTypeVo> {
        return client.get("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
        }.body<List<DrinkTypeVo>>()
    }

    //GET - Une boisson selon sont id
    suspend fun getDrink(id : Int): DrinkTypeVo {
        return client.get("$baseUrl/drink/$id") {
            contentType(ContentType.Application.Json)
        }.body<DrinkTypeVo>()
    }

    // POST - Créer une nouvelle boisson
    suspend fun createDrink(drink: DrinkTypeVo): DrinkTypeVo {
        return client.post("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
            setBody(drink)
        }.body<DrinkTypeVo>()
    }

    // PUT - Mettre à jour une boisson existante
    suspend fun updateDrink( drink: DrinkTypeVo): DrinkTypeVo {
        return client.patch("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
            setBody(drink)
        }.body<DrinkTypeVo>()
    }

    // DELETE - Supprimer une boisson
    suspend fun deleteDrink(id: Int) {
        client.delete("$baseUrl/drink/$id") {
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun getBars(): List<BarVo> {
        return client.get("$baseUrl/bar") {
            contentType(ContentType.Application.Json)
        }.body<List<BarVo>>()
    }

    suspend fun postBar(bar: SimpleBarVo): HttpResponse {
        return client.post("$baseUrl/bar") {
            contentType(ContentType.Application.Json)
            setBody(bar)
        }.body<HttpResponse>()
    }

    suspend fun getBarById(barId: Int): BarVo {
        return client.get("$baseUrl/bar/$barId") {
            contentType(ContentType.Application.Json)
        }.body<BarVo>()
    }

    suspend fun deleteBarById(barId: Int): HttpResponse {
        return client.delete("$baseUrl/bar/$barId") {
            contentType(ContentType.Application.Json)
        }.body<HttpResponse>()
    }

    suspend fun getPhotosByBar( barId: Int): List<BarPhoto> {
        return runCatching {
            val responseText: String = client.get("$baseUrl/photos/bar/$barId") {
                contentType(ContentType.Application.Json)
            }.body()

            Json { isLenient = true; ignoreUnknownKeys = true }
                .decodeFromString<List<BarPhoto>>(responseText)
        }.getOrElse {
            Log.e("MeetMyBarAPI", "Erreur de parsing JSON: ${it.message}")
            emptyList()
        }
    }

    suspend fun getPhotoById(id: Int): ByteArray {
        return client.get("$baseUrl/photos/download/$id") {
            contentType(ContentType.Image.JPEG)
        }.body<ByteArray>()
    }

    suspend fun addDrinkToBar(idBar: Int, idDrink: Int, volume: Double, price: Double): HttpResponse {
        return client.post("$baseUrl/drink/bar?idBar=$idBar&idDrink=$idDrink&volume=$volume&price=$price") {
            contentType(ContentType.Application.Json)
        }.body<HttpResponse>()
    }

    /**
     * Supprime un lien entre un bar et une boisson avec un volume spécifique
     */
    suspend fun deleteBarDrinkLink(idBar: Int, idDrink: Int, volume: Int): HttpResponse {
        return client.delete("$baseUrl/drink/bar?idBar=$idBar&idDrink=$idDrink&volume=$volume") {
            contentType(ContentType.Application.Json)
        }.body<HttpResponse>()
    }

    suspend fun modifyBar(bar: BasicBarVo): HttpResponse {
        return client.patch("$baseUrl/bar") {
            contentType(ContentType.Application.Json)
            setBody(bar)
        }.body<HttpResponse>()
    }

    suspend fun updateDrinkPrice(idBar: Int, idDrink: Int, volume: String, newPrice: String): HttpResponse {
        return client.patch("$baseUrl/drink/bar?idBar=$idBar&idDrink=$idDrink&volume=$volume&newPrice=$newPrice") {
            contentType(ContentType.Application.Json)
        }.body<HttpResponse>()
    }
}

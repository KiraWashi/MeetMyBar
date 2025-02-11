package com.example.frontend.data.api

import com.example.frontend.data.vo.DrinkVo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent


class MeetMyBarAPI(
    private val client: HttpClient,
    private val baseUrl: String = " http://leop.letareau.fr:8080/",
) : KoinComponent {

    //GET - Toutes les boissons de la base
    suspend fun getDrinks(): List<DrinkVo> {
        return client.get("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
        }.body<List<DrinkVo>>()
    }

    //GET - Une boisson selon sont id
    suspend fun getDrink(id : Int): DrinkVo {
        return client.get("$baseUrl/drink/$id") {
            contentType(ContentType.Application.Json)
        }.body<DrinkVo>()
    }

    // POST - Créer une nouvelle boisson
    suspend fun createDrink(drink: DrinkVo): DrinkVo {
        return client.post("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
            setBody(drink)
        }.body<DrinkVo>()
    }

    // PUT - Mettre à jour une boisson existante
    suspend fun updateDrink( drink: DrinkVo): DrinkVo {
        return client.patch("$baseUrl/drink") {
            contentType(ContentType.Application.Json)
            setBody(drink)
        }.body<DrinkVo>()
    }

    // DELETE - Supprimer une boisson
    suspend fun deleteDrink(id: Int) {
        client.delete("$baseUrl/drink/$id") {
            contentType(ContentType.Application.Json)
        }
    }
}

package com.example.frontend.core

import android.util.Log
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.repository.DrinkRepository
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import com.example.frontend.presentation.biere.ListBiereViewModel
import com.example.frontend.presentation.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Android.create() }
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = true) }

    // API
    single { MeetMyBarAPI(get()) }

    // Repository
    single<DrinkRepositoryInterface> { DrinkRepository(get()) }

    // View Model
    viewModel { HomeViewModel() }
    viewModel { ListBiereViewModel(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = CustomHttpLogger()
                level = LogLevel.ALL
            }
        }
    }

class CustomHttpLogger() : Logger {
    override fun log(message: String) {
        Log.i("CustomHttpLogger", "message : $message")
    }
}

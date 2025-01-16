package com.example.frontend

import android.app.Application
import com.example.frontend.core.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MeetMyBarApplication : Application() {
    companion object {
        var instance: MeetMyBarApplication? = null
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        startKoin {
            androidContext(this@MeetMyBarApplication)
            modules(appModule)
        }
    }
}
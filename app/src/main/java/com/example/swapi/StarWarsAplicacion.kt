package com.example.swapi

import android.app.Application
import com.example.swapi.datos.ContenedorApp
import com.example.swapi.datos.StarWarsContenedorApp

class StarWarsAplicacion: Application() {
    lateinit var contenedorApp: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedorApp = StarWarsContenedorApp()
    }
}
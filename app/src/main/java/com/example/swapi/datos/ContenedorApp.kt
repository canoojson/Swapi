package com.example.swapi.datos

import com.example.swapi.conexion.ServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp {
    val navesRepositorio : NavesRepositorio
}

class StarWarsContenedorApp : ContenedorApp {
    private  val baseUrl = "https://swapi.dev/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()
    private val servicioRetrofit: ServicioApi by lazy {
        retrofit.create(ServicioApi::class.java)
    }
    override val navesRepositorio: NavesRepositorio by lazy {
        ConexionNavesRepositorio(servicioRetrofit)

    }
}
package com.example.swapi.conexion

import com.example.swapi.modelo.Respuesta
import retrofit2.http.GET

interface ServicioApi {
    @GET("starships")
    suspend fun obtenerNaves(): Respuesta
}
package com.example.swapi.datos

import com.example.swapi.conexion.ServicioApi
import com.example.swapi.modelo.Respuesta

interface NavesRepositorio {
    suspend fun obtenerNaves(): Respuesta
}

class ConexionNavesRepositorio(
    private val servicioApi: ServicioApi
): NavesRepositorio {
    override suspend fun obtenerNaves(): Respuesta = servicioApi.obtenerNaves()
}
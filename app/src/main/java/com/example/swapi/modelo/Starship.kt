package com.example.swapi.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Starship(
    @SerialName(value = "name")
    val nombre: String,
    @SerialName(value = "model")
    val modelo: String,
    @SerialName(value = "films")
    val peliculas: List<String>
)

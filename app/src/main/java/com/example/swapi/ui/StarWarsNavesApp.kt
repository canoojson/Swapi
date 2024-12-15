package com.example.swapi.ui

import androidx.annotation.StringRes
import com.example.swapi.R

enum class Pantallas(@StringRes val titulo: Int) {
    PantallaDatos(titulo = R.string.inicio),
    ListarNaves(titulo = R.string.lista_naves)
}
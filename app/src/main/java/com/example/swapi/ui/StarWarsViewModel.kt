package com.example.swapi.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.swapi.StarWarsAplicacion
import com.example.swapi.datos.NavesRepositorio
import com.example.swapi.modelo.Respuesta
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface StarWarsUIState {
    data class Success(val respuesta: Respuesta) : StarWarsUIState
    object Error : StarWarsUIState
    object Cargando : StarWarsUIState
}

class StarWarsViewModel(private val navesRepositorio: NavesRepositorio) : ViewModel() {
    var starWarsUIState: StarWarsUIState by mutableStateOf(StarWarsUIState.Cargando)
        private set
    init {
        obtenerNave()
    }

    fun obtenerNave() {
        viewModelScope.launch {
            starWarsUIState = StarWarsUIState.Cargando
            starWarsUIState = try{
                val listaNaves = navesRepositorio.obtenerNaves()
                StarWarsUIState.Success(listaNaves)
            }catch (e: IOException){
                StarWarsUIState.Error
            }catch (e: HttpException){
                StarWarsUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as StarWarsAplicacion)
                val navesRepositorio = aplicacion.contenedorApp.navesRepositorio
                StarWarsViewModel(navesRepositorio = navesRepositorio)
            }
        }
    }
}
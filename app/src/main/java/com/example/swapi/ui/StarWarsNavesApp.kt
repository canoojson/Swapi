package com.example.swapi.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swapi.ListarNaves
import com.example.swapi.ListarPeliculas
import com.example.swapi.PantallaDatos
import com.example.swapi.PantallaSuccess
import com.example.swapi.R

enum class Pantallas(@StringRes val titulo: Int) {
    PantallaDatos(titulo = R.string.inicio),
    PantallaSuccess(titulo = R.string.success),
    ListarPeliculas(titulo = R.string.lista_naves)
}

@Composable
fun StarWarsNavesApp(
    viewModel: StarWarsViewModel = viewModel(factory = StarWarsViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.PantallaDatos.name
    )



    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Pantallas.PantallaDatos.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Pantallas.PantallaDatos.name){
                PantallaDatos(
                    viewModel = viewModel,
                    onSucess = {
                        navController.navigate(Pantallas.PantallaSuccess.name)
                    }
                )
            }
            composable(route = Pantallas.PantallaDatos.name){
                PantallaSuccess(
                    onNavePulsado = {
                        navController.navigate(Pantallas.ListarNaves.name)
                    }
                )
            }
            composable(route= Pantallas.ListarPeliculas.name){
                ListarPeliculas()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(pantallaActual.titulo)) },
        colors = TopAppBarDefaults.topAppBarColors(),
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.atras)
                    )
                }
            }
        },
        modifier = modifier
    )
}
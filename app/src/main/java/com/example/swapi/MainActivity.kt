package com.example.swapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.swapi.modelo.Respuesta
import com.example.swapi.modelo.Starship
import com.example.swapi.ui.StarWarsNavesApp
import com.example.swapi.ui.StarWarsUIState
import com.example.swapi.ui.StarWarsViewModel
import com.example.swapi.ui.theme.SwapiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwapiTheme {
                StarWarsNavesApp()
            }
        }
    }
}

@Composable
fun PantallaDatos(modifier: Modifier = Modifier,
                  viewModel: StarWarsViewModel = viewModel(factory = StarWarsViewModel.Factory),
                  onSucess: () -> Unit
) {
    val starWarsUIState = viewModel.starWarsUIState

    when(starWarsUIState) {
        is StarWarsUIState.Cargando -> PantallaCargando(modifier = modifier.fillMaxSize())
        is StarWarsUIState.Success -> onSucess()/*PantallaSuccess(
            respuesta = starWarsUIState.respuesta,
            modifier = modifier.fillMaxSize()
        )*/
        is StarWarsUIState.Error -> PantallaError(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun PantallaCargando(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.cargando),
        contentDescription = stringResource(R.string.cargando)
    )
}

@Composable
fun PantallaError(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.error),
        contentDescription = stringResource(R.string.error_de_conexion)
    )
}

@Composable
fun PantallaSuccess(respuesta: Respuesta, modifier: Modifier = Modifier, onNavePulsado: () -> Unit) {
    LazyColumn(modifier = modifier) {
        items(respuesta.resultados) { starship ->
            var mostrarDialogo by remember { mutableStateOf(false) }
            Card(modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                .clickable { /*mostrarDialogo = true*/}) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = starship.nombre,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = starship.modelo,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            if(mostrarDialogo) {
                /*
                Dialog(onDismissRequest = { mostrarDialogo = false }) {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Column(modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally){
                            for(pelicula in starship.peliculas) {
                                Text(
                                    text = pelicula,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Button(onClick = { mostrarDialogo = false }) {
                                Text(text = "Cerrar")
                            }
                        }
                    }
                }*/
                ListarPeliculas(starship)
            }
        }
    }
}

@Composable
fun ListarPeliculas(starship: Starship){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        for(pelicula in starship.peliculas) {
            Text(
                text = pelicula,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

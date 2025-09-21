package com.example.aplicacionclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionclicker.ui.theme.AplicacionClickerTheme

const val KILLS_PARA_NIVEL = 3
const val KILLS_PARA_BOSS  = 15

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionClickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EnemigoClicker(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun EnemigoClicker(modifier: Modifier = Modifier) {
    // Estado jugador
    var nivel by remember { mutableStateOf(1) }
    var danio by remember { mutableStateOf(1) }
    var killsTotales by remember { mutableStateOf(0) }
    var kills by remember { mutableStateOf(0) }
    var dificultad by remember { mutableStateOf(1) }
    var contadorDificultad by remember { mutableStateOf(0) }

    // Estado enemigo
    var esBoss by remember { mutableStateOf(false) }
    var vidaEnemigoMaxima by remember { mutableStateOf((5..20).random() * dificultad) }
    var vidaEnemigoActual by remember { mutableStateOf(vidaEnemigoMaxima) }
    var enemigoImagen by remember { mutableStateOf(R.drawable.enemigo1) }

    // Mensajes
    var mensaje by remember { mutableStateOf<String?>(null) }
    var mensajeColor by remember { mutableStateOf(Color.Black) }

    // Puntos de nivel
    var puntosNivel by remember { mutableStateOf(0) }

    val normalEnemigo = listOf(
        R.drawable.enemigo1,
        R.drawable.enemigo2,
        R.drawable.enemigo3
    )
    val bossEnemigo = listOf(
        R.drawable.boss1,
        R.drawable.boss2
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Información del jugador
        Text(text = "Nivel: $nivel · Daño: $danio · Kills: $killsTotales · Puntos: $puntosNivel · Dificultad: $dificultad")

        Spacer(modifier = Modifier.height(12.dp))

        // Imagen enemigo
        Image(
            painter = painterResource(id = enemigoImagen),
            contentDescription = "Enemigo",
            modifier = Modifier.size(260.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Vida: $vidaEnemigoActual/$vidaEnemigoMaxima")

        Spacer(modifier = Modifier.height(16.dp))

        // Botones en fila
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            // Botón atacar
            Button(
                onClick = {
                    vidaEnemigoActual -= danio

                    if (vidaEnemigoActual <= 0) {
                        killsTotales++

                        if (esBoss) {
                            puntosNivel += 3
                            mensaje = "Has derrotado a un boss! (+3 puntos)"
                            mensajeColor = Color.Black
                            esBoss = false

                            // Generar nuevo enemigo normal con dificultad actual
                            vidaEnemigoMaxima = (5..20).random() * dificultad
                            vidaEnemigoActual = vidaEnemigoMaxima
                            enemigoImagen = normalEnemigo.random()
                            kills++
                        } else {
                            kills++
                            mensaje = "Has derrotado a un enemigo!"
                            mensajeColor = Color.Black

                            if (kills % KILLS_PARA_NIVEL == 0) {
                                puntosNivel++
                            }

                            if (kills >= KILLS_PARA_BOSS) {
                                esBoss = true
                                mensaje = "Ha aparecido un boss!"
                                mensajeColor = Color.Black

                                // Vida del boss con dificultad actual
                                vidaEnemigoMaxima = (50..100).random() * dificultad
                                vidaEnemigoActual = vidaEnemigoMaxima
                                enemigoImagen = bossEnemigo.random()
                                kills = 0
                            } else {
                                // Generar nuevo enemigo normal con dificultad actual
                                vidaEnemigoMaxima = (5..20).random() * dificultad
                                vidaEnemigoActual = vidaEnemigoMaxima
                                enemigoImagen = normalEnemigo.random()
                            }
                        }
                    }
                }
            ) {
                Text("Atacar")
            }

            // Botón subir nivel
            Button(
                onClick = {
                    if (puntosNivel > 0) {
                        puntosNivel--
                        nivel++
                        danio++
                        mensaje = "Has subido de nivel!"
                        mensajeColor = Color.Black
                        contadorDificultad++

                        // Cada 5 niveles sube la dificultad y genera enemigo nuevo
                        if (contadorDificultad == 5) {
                            dificultad++
                            contadorDificultad = 0

                        }

                    } else {
                        mensaje = "No tienes puntos suficientes!"
                        mensajeColor = Color.Red
                    }
                }
            ) {
                Text("Subir nivel")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mensaje debajo de los botones
        mensaje?.let {
            Text(
                text = it,
                color = mensajeColor
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EnemyClickerPreview() {
    EnemigoClicker()
}

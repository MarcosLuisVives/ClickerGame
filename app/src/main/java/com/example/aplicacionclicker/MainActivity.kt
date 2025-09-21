package com.example.aplicacionclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionclicker.ui.theme.AplicacionClickerTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color



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
    var nivel by remember { mutableStateOf(1) }
    var danio by remember { mutableStateOf(1) }
    var kills by remember { mutableStateOf(0) }
    var killsTotales by remember { mutableStateOf(0) }
    var esBoss by remember { mutableStateOf(false) }
    var vidaEnemigoMaxima by remember { mutableStateOf((5..20).random()) }
    var vidaEnemigoActual by remember { mutableStateOf(vidaEnemigoMaxima) }
    var enemigoImagen by remember { mutableStateOf(R.drawable.enemigo1) }

    var mensaje by remember { mutableStateOf<String?>(null) }
    var mensajeColor by remember { mutableStateOf(Color.Black) }

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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )  {

        Text(text = "Nivel: $nivel  •  Daño: $danio  •  Normales: $kills  •  Totales: $killsTotales")

        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(id = enemigoImagen),
            contentDescription = "Enemigo",
            modifier = Modifier.size(260.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Vida: $vidaEnemigoActual/$vidaEnemigoMaxima")
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            Button(
                onClick = {
                    vidaEnemigoActual -= danio
                    if (vidaEnemigoActual <= 0) {
                        killsTotales++
                        if (esBoss) {
                            puntosNivel += 3
                            mensaje = "Has derrotado a un boss!"
                            esBoss = false
                            vidaEnemigoMaxima = (5..20).random()
                            vidaEnemigoActual = vidaEnemigoMaxima
                            enemigoImagen = normalEnemigo.random()
                            kills++
                        } else {
                            kills++
                            mensaje = "Has derrotado a un enemigo!"
                            if (kills % KILLS_PARA_NIVEL == 0) {
                                puntosNivel++
                                mensaje = "Has subido de nivel!"
                            }
                            if (kills >= KILLS_PARA_BOSS) {
                                esBoss = true
                                mensaje = "Ha aparecido un boss!"
                                vidaEnemigoMaxima = (50..100).random()
                                vidaEnemigoActual = vidaEnemigoMaxima
                                enemigoImagen = bossEnemigo.random()
                                kills = 0

                            } else {
                                vidaEnemigoMaxima = (5..20).random()
                                vidaEnemigoActual = vidaEnemigoMaxima
                                enemigoImagen = normalEnemigo.random()

                            }
                        }
                    }
                }
            )
            {
                Text("Atacar")
            }

                Button(
                    onClick = {
                        if (puntosNivel > 0) {
                            puntosNivel--
                            nivel++
                            danio++
                            mensaje = "Has subido de nivel!"
                            mensajeColor = Color.Black
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

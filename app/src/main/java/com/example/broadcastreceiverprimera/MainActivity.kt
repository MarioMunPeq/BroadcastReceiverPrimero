package com.example.broadcastreceiverprimera

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declaración variables
    private lateinit var textoRed: TextView
    private lateinit var connectivityManager: ConnectivityManager

    // BroadcastReceiver para manejar cambios en la conectividad de red https://developer.android.com/reference/android/content/BroadcastReceiver
    private val estadoRedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            actualizarTipoRed() // Llamo al método para comprobar tipo conexión
        }
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar variables
        textoRed = findViewById(R.id.wifiText)
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager // https://developer.android.com/reference/android/net/ConnectivityManager

        // Registrar el BroadcastReceiver para cambios en la conectividad de red
        registerReceiver(estadoRedReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        actualizarTipoRed() // Para actualizar el estado nada más abrir
    }

    /**
     * Cuando se destruye la actividad, se "desuscribe" del broadcast
     */
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(estadoRedReceiver)
    }


    /**
     * Actualiza el estado de la red
     */
    private fun actualizarTipoRed() {
        // Obtener las capacidades de la red activa https://developer.android.com/reference/android/net/NetworkCapabilities
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }

        // Determinar el estado de la red según las capacidades
        val status: String = when {

            // networkCapabilities son las "capacidades de la red", la "?" sirve para que solo llame al hasTransport si no es nulo
            // hasTransport sirve para comprobar que tipo de red tiene, devuelve un booleano
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> {
                "Conectado a red Wi-Fi"
            }

            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> {
                "Conectado a datos móviles"
            }

            else -> "No estás conectado a ninguna red"
        }
        textoRed.text = status // Cambiar texto según conexión
    }

}
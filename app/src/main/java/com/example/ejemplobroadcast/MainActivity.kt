package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplobroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Van configurar un broadcast a partir
    // de la Clase BroadcastReceiver
    private val getAirplaneMode = object: BroadcastReceiver() {
        //Configurar o implementar el metodo que a ustedes
        //les permite recibir los eventos que vienen del sistema
        //y procesar esos datos y hacer la logica que desean
        //a partir de ello
        override fun onReceive(context: Context?, intent: Intent?) {
            val airPlaneMode = intent?.getBooleanExtra("state", false)
            //aca el evento del sistema ya nos ha hablado, nos ha dicho si esta o no
            //habilitado el modo avion
            airPlaneMode?.let {
                val message = if (it) "Modo Avion habilitado" else "modo Avion deshabilitado"
                binding.txtModoAvion.text = message
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        //suscribir el broadcast
        //como segundo parámetro deben enviar
        //un intent filter .. filtro que buscará
        //asegurar que la acción a ejecutar sea la correcta a ese evento
        registerReceiver(getAirplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        //quitas las suscripción del broadcast
        //para no consumir recursos innecesariamente
        unregisterReceiver(getAirplaneMode)
    }
}













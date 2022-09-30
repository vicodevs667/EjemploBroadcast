package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplobroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val getAirplaneMode = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            //puede ser true or false
            //true si esta habilitado el modo avion
            val airplaneMode = intent?.getBooleanExtra("state", false)
            airplaneMode?.let {
                val mensaje = if (it) "Modo Avión Activado" else "Modo Avión Desactivado"
                binding.txtModoAvion.text = mensaje
            }
        }
    }

    private val getTimeChange = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val tiempo = "El tiempo ha cambiado en el celular"
            binding.txtTiempo.text = tiempo
        }
    }

    private val getWifiMode = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //EXTRA_WIFI_STATE = llave del registro entero que te informa los estados del wifi
            val wifiMode = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            wifiMode?.let {
                binding.txtWifi.text = when(it) {
                    WifiManager.WIFI_STATE_ENABLED -> "Wifi habilitado"
                    WifiManager.WIFI_STATE_DISABLED -> "Wifi deshabilitado"
                    WifiManager.WIFI_STATE_UNKNOWN -> "Servicio no reconocido"
                    else -> "Posible error en el servicio Wifi"
                }
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
        registerReceiver(getAirplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(getTimeChange, IntentFilter(Intent.ACTION_TIME_TICK))
        registerReceiver(getWifiMode, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(getAirplaneMode)
        unregisterReceiver(getTimeChange)
        unregisterReceiver(getWifiMode)
    }
}










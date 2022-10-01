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

    //Definir un objeto de tipo BroadcastReceiver
    //donde directamente aplicaremos la implementación
    //o sobreescritura de un método clave en la
    //configuración de un Broadcast que es el método
    //onReceive
    private val getAirplaneMode = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val airplaneMode = intent?.getBooleanExtra("state", false)
            airplaneMode?.let {
                val message = if (it) "Modo Avión activado" else "Modo Avión desactivado"
                binding.txtModoAvion.text = message
            }
        }
    }

    // Configurar un Broadcast Receiver para el evento de cambio de tiempo
    // a este se le denomina Time Tick
    private val getTimeChange = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.txtTimeTick.text = "El tiempo ha cambiado"
        }
    }
    //Vamos a configurar el BroadcastReceiver
    //para saber cuando el Wifi esta habilitado o no
    private val getWifiMode = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //Wifi trabaja pasando a traves de un registro en el
            //Intent un dato de tipo numérico, ese dato
            //te va a indicar aspectos como si esta habilitado,
            //si no esta habilitado, configuraciones de la red,
            //si el servicio esta dañado o no se reconoce
            //Servicio Wifi: se gestiona a través de un controlador
            //que es una clase que lo administra -> WifiManager
            val wifiMode = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            wifiMode?.let {
                val message = when (it) {
                   WifiManager.WIFI_STATE_ENABLED -> "WiFi habilitado"
                   WifiManager.WIFI_STATE_DISABLED -> "Wifi esta deshabilitado"
                   WifiManager.WIFI_STATE_UNKNOWN -> "Servicio Wifi error desconocido"
                   else -> "No dispones de WiFi, actualizate"
                }
                binding.txtModoWifi.text = message
            }
        }
    }


    override fun onStart() {
        super.onStart()
        //Registrar el BroadcastReceiver
        registerReceiver(getAirplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(getTimeChange, IntentFilter(Intent.ACTION_TIME_TICK))
        registerReceiver(getWifiMode, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }


    override fun onStop() {
        super.onStop()
        //Quitar el registro
        unregisterReceiver(getAirplaneMode)
        unregisterReceiver(getTimeChange)
    }
}












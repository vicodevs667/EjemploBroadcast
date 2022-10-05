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

    //variable que sirva para configurar un broadcastReceiver
    //en este caso particular para comunicarse con el sistema
    //y saber si esta activo o no el modo avion
    private val getAirplaneMode = object: BroadcastReceiver() {
        //Al momento de configurar un BroadcastReceiver
        //es fundamental que ustedes sobreescriban el metodo
        //llamado onReceive -> posibilidad de recibir información
        // del evento del sistema y ustedes definen la lógica
        // que desean aplicar a partir de ese evento y esa información
        override fun onReceive(context: Context?, intent: Intent?) {
            val airplaneMode = intent?.getBooleanExtra("state", false)
            airplaneMode?.let {
                val mensaje = if (it) "Modo Avión Activo" else "Modo Avión desactivado"
                binding.txtModoAvion.text = mensaje
            }
        }
    }

    //Configurar un BroadcastReceiver referido a tratar
    //los cambios de tiempo en el sistema -> Android
    //a esto se conoce como Time Tick
    private val getTimeChange = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = "el tiempo ha cambiado"
            binding.txtTimeTick.text = message
        }
    }

    //Crear una variable para configurar el Broadcast que nos
    //permitirá comunicarse con el servicio de Wifi.....
    //Para manejar el Wifi se va a usar una clase propia que
    //alberga todas las configuraciones y tratamientos para este servicio
    //es un controlador del servicio Wifi a este se lo conoce como
    //WifiManager
    private val getWifiMode = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //EXTRA_WIFI_STATE -> llave del registro temporal del dato del estado del wifi
            //PAra el wifi el valor entero que envia, su valor por defecto
            //tiene que referir a que no puede resolver el servicio y este es desconocido
            val wifiMode = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            wifiMode?.let {
                val message = when(it) {
                    WifiManager.WIFI_STATE_ENABLED -> "Wifi Habilitado"
                    WifiManager.WIFI_STATE_DISABLED -> "Wifi Deshabilitado"
                    WifiManager.WIFI_STATE_UNKNOWN -> "Error en el servicio de Wifi"
                    else -> "Comprate un nuevo celular"
                }
                binding.txtModoWifi.text = message
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
        //Registrar su BroadcastReceiver
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












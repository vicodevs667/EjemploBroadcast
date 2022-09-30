package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.ejemplobroadcast.databinding.ActivityBatteryBinding

//Para que se comporte como una clase de tipo
//BroadcastReceiver deben heredar una clase abstracta
//de tipo BroadcastReceiver()
class MyBroadcast(
    val bg: ActivityBatteryBinding
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //onReceive va a funcionar para recibir
        //los mensajes de diferentes servicios que configuremos
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryStatus(intent)
        }
    }

    private fun showBatteryStatus(intent: Intent?) {
        val batteryStatus = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL,0)
        batteryStatus?.let {
            val message = "El nivel de la bateria es $it%"
            bg.apply {
                txtPorcentajeBateria.text = message
                pbNivelBateria.progress = it
            }
        }
    }
}













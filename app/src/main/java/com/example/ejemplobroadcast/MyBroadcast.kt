package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.ejemplobroadcast.databinding.ActivityBatteryBinding

//Para que esta clase se comporte como un
//Broadcast Receiver hay que extender
//una clase abstracta que ustedes ya la
//han usado 3 veces en el ejemplo BroadcastReceiver()
class MyBroadcast(
    private val bindingObject: ActivityBatteryBinding
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryLevel(intent)
            Intent.ACTION_BATTERY_LOW -> configureBatteryLow(intent)
        }
    }

    private fun configureBatteryLow(intent: Intent?) {
        //El dato que les llega a través del archivo Extras del Intent
        //es un booleano
        val batteryLow = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
        batteryLow?.let {
            bindingObject.txtMensajeBateria.text = "Alerta Bateria Baja"
        }

    }

    private fun showBatteryLevel(intent: Intent?) {
        //un valor entero que te llega en el Extras del Intent
        //que el valor del nivel de la bateria
        //La bateria tiene un gestor Llamado BatteryManager
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let {
            val message = "Porcentaje bateria $it%"
            bindingObject.txtPorcentajeBateria.text = message
            bindingObject.pbNivelBateria.progress = it
        }
    }
}














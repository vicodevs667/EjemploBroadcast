package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.ejemplobroadcast.databinding.ActivityBateriaBinding

//Para que esta clase se comporte como
//un Broadcast Receiver debe extender
//o heredar la clase abstracta que configura
//ello.
//BroadcastReceiver() -> clase abstracta
class MyBroadcast(
    val bindingObject: ActivityBateriaBinding
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryStatus(intent)
            Intent.ACTION_BATTERY_LOW -> showAlertBatteryLow(intent)
        }
    }

    private fun showAlertBatteryLow(intent: Intent?) {
        //La bateria baja es tratada a partir de un
        //estado booleano true cuando esta baja
        //false cuando esta normal en carga
        val batteryLevel = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
    }

    private fun showBatteryStatus(intent: Intent?) {
        //Bateria y su cambio de nivel se maneja
        //un valor entero en rango de valores
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let {
            bindingObject.txtPorcentajeBateria.text = "$it%"
            bindingObject.pbNivelBateria.progress = it
        }
    }
}













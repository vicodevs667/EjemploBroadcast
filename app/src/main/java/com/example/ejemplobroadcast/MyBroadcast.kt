package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.ejemplobroadcast.databinding.ActivityBatteryBinding

//Para que esta clase se comporte como un
//BroadcastReceiver debes extender o heredar
//una clase abstracta que ustedes ya han utilizado
//3 veces en el ejercicio anterior
class MyBroadcast(
    private val bindingObject: ActivityBatteryBinding
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryLevel(intent)
        }
    }

    private fun showBatteryLevel(intent: Intent?) {
        //Cuando se trata del nivel de la bateria, el servicio del sistema
        //les envia a través de un intent un valor entero que representa
        //el porcentaje de bateria restante
        //El tema de la bateria generalmente es gestionado y configurado
        //en un clase que controla todos estos aspectos y se llama BatteryManager
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let {
            val porcentaje = "$it% bateria"
            bindingObject.txtPorcentajeBateria.text = porcentaje
            bindingObject.pbNivelBateria.progress = it
        }
    }
}






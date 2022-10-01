package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

//Para que esta clase se comporte como un
//Broadcast Receiver hay que extender
//una clase abstracta que ustedes ya la
//han usado 3 veces en el ejemplo BroadcastReceiver()
class MyBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Configurar nivel de bateria")
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryLevel(intent)
        }
    }

    private fun showBatteryLevel(intent: Intent) {

    }
}














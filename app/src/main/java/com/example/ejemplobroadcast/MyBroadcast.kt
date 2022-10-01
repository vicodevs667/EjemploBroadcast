package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

//Para que esta clase se comporte como
//un Broadcast Receiver debe extender
//o heredar la clase abstracta que configura
//ello.
//BroadcastReceiver() -> clase abstracta
class MyBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryStatus(intent)
        }
    }

    private fun showBatteryStatus(intent: Intent?) {
        //Bateria y su cambio de nivel se maneja
        //un valor entero en rango de valores
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let {

        }
    }
}













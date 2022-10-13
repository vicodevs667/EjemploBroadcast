package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ejemplobroadcast.databinding.ActivityBatteryBinding
import kotlin.math.round

//Para que esta clase se comporte como un
//BroadcastReceiver debes extender o heredar
//una clase abstracta que ustedes ya han utilizado
//3 veces en el ejercicio anterior
class MyBroadcast(
    private val bindingObject: ActivityBatteryBinding
): BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryLevel(intent)
            Intent.ACTION_BATTERY_LOW -> evaluateLowBattery(context, intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun evaluateLowBattery(context: Context?, intent: Intent?) {
        //El intent que resuelve el tema de la bateria baja
        //maneja un dato en su registro temporal de tipo booleano
        val lowBattery = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
        lowBattery?.let {
            bindingObject.txtMensajeBateria.text = "Alerta Bateria Baja"
            configureScreenBrightness(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun configureScreenBrightness(context: Context?) {
        //No pueden usar recursos de hardware y configuraciones del sistema
        //a libre gusto, si no hay permisos o no estan habilitadas las opciones
        //no pueden ejecutar esos métodos
        if (hasWriteSettingsEnabled(context)) {
            //1: el nivel de brillo se maneja de 0 a 255
            //2: por defecto el brillo se ajusta automáticamente, por ende primero hay que cambiar a modo manual
            val screenBrightnessLevel = 20
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                screenBrightnessLevel
            )
            val percentage = screenBrightnessLevel.toDouble() / 255
            Toast.makeText(context, "Nivel ${round(percentage * 100)}%", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "No puedes configurar los settings", Toast.LENGTH_SHORT).show()
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasWriteSettingsEnabled(context: Context?): Boolean {
        return Settings.System.canWrite(context)
    }
}





















package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.example.ejemplobroadcast.databinding.ActivityBateriaBinding

//Para que esta clase se comporte como
//un Broadcast Receiver debe extender
//o heredar la clase abstracta que configura
//ello.
//BroadcastReceiver() -> clase abstracta
class MyBroadcast(
    val bindingObject: ActivityBateriaBinding
): BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryStatus(intent)
            Intent.ACTION_BATTERY_LOW -> showAlertBatteryLow(context, intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showAlertBatteryLow(context: Context?, intent: Intent?) {
        //La bateria baja es tratada a partir de un
        //estado booleano true cuando esta baja
        //false cuando esta normal en carga
        val batteryLevel = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
        batteryLevel?.let {
            bindingObject.txtMensajeBateria.text = "Bateria baja"
            changeScreenBrightness(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeScreenBrightness(context: Context?) {
        //El brillo esta definido en un rango de 0 a 255
        //donde 255 es el brillo máximo que puede haber
        val screenBrightness = 15
        //No puedes alterar un servicio del sistema
        //como son los settings de tu celular
        //a libre gusto si no tienes los permisos habilitados
        val brightnessPermission = hasWriteSettingsPermission(context)
        if (brightnessPermission) {
            TODO("Implementar cambio de brillo")
        } else {
            TODO("Implementar alterta cuando no tienes permiso")
        }
    }

    //Este metodo lo único que hace es validar si tienes
    //o no tienes permisos
    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasWriteSettingsPermission(context: Context?): Boolean {
        return Settings.System.canWrite(context)
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













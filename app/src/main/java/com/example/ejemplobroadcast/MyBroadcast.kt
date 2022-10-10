package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ejemplobroadcast.databinding.ActivityBateriaBinding
import kotlin.math.round

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
            //Si hay permisos cambiamos el brillo
            //1: el brillo se maneja en rangos numéricos de 0 a 255
            // siendo 255 el brillo máximo que se le puede dar a la pantalla
            //2: por defecto el brillo se maneja o ajusta automáticamente
            //quiere decir que los niveles son manejados por el sistema...

            //COnfigurando el brillo para que sea en modo manual
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            //Configurando el brillo para darle el nivel que ustedes desean
            // entre 0 y 255
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                screenBrightness
            )
            val brightnessPercentage = screenBrightness.toDouble() / 255
            Toast.makeText(context, "${round(brightnessPercentage)}%",
            Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "No hay permisos, no se puede habilitar brillo",
            Toast.LENGTH_SHORT).show()
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













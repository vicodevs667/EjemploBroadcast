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

//Para que se comporte como una clase de tipo
//BroadcastReceiver deben heredar una clase abstracta
//de tipo BroadcastReceiver()
class MyBroadcast(
    val bg: ActivityBatteryBinding
): BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        //onReceive va a funcionar para recibir
        //los mensajes de diferentes servicios que configuremos
        when(intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> showBatteryStatus(intent)
            Intent.ACTION_BATTERY_LOW -> showBatteryLow(context, intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showBatteryLow(context: Context?, intent: Intent?) {
        val batteryStatus = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
        //Cuando la bateria esta baja vamos a bajar su brillo de la pantalla
        //a un mínimo estimado
        batteryStatus?.let {
            bg.txtMensajeBateria.text = "Bateria baja"
            //Brillo de pantalla -> 0 a 255
            //255: es el máximo de brillo a su pantalla
            val screenBrightness = 20
            changeScreenBrightness(context, screenBrightness)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeScreenBrightness(context: Context?, screenBrightness: Int) {
        val writePermissions = hasEnabledWritePermissions(context)
        if (writePermissions) {
            //1: El brillo de la pantalla esta configurado automáticamente
            //2: si quieren jugar con el brillo primero tienen que cambiar esa configuración
            //Es cambiar el formato del brillo de la pantalla de modo automático
            //a modo manual, para tener el control
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            //Ajustar el brillo de la pantalla
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                screenBrightness
            )
            val porcentajeBrillo = screenBrightness.toDouble() / 255
            Toast.makeText(
                context,
                "Brillo al: ${round(porcentajeBrillo)}%",
                Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(context, "No tienes permisos sorry", Toast.LENGTH_SHORT).show()
    }

    //Evaluará si tienen los permisos de escritura en las
    //configuraciones del sistema habilitadas
    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasEnabledWritePermissions(context: Context?): Boolean {
        return Settings.System.canWrite(context)
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













package com.example.ejemplobroadcast

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.ejemplobroadcast.databinding.ActivityBateriaBinding

class BateriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBateriaBinding
    private lateinit var myBroadcast: MyBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBateriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //inicializar una instancia de su broadcast receiver
        myBroadcast = MyBroadcast(binding)
        //Vamos a configurar el click del FAB
        binding.fabPermission.setOnClickListener {
            enableSettingsPermission()
        }
    }
    //Registrar el broadcast receiver
    override fun onStart() {
        super.onStart()
        registerReceiver(myBroadcast, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        registerReceiver(myBroadcast, IntentFilter(Intent.ACTION_BATTERY_LOW))
        val intent = Intent(this, myBroadcast::class.java)
        sendBroadcast(intent)
    }

    //Quitar el registro de broadcast receiver
    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcast)
    }

    private fun enableSettingsPermission() {
        //a través de un intent
        //estan lanzando una pantalla del sistema android
        //en este caso la pantalla de settings de las apps
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        startActivity(intent)
    }
}














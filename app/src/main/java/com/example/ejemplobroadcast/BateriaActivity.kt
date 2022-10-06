package com.example.ejemplobroadcast

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
    //Registrar el broadcast receiver
    override fun onStart() {
        super.onStart()
        registerReceiver(myBroadcast, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val intent = Intent(this, myBroadcast::class.java)
        sendBroadcast(intent)
    }

    //Quitar el registro de broadcast receiver
    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcast)
    }
}














package com.example.ejemplobroadcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplobroadcast.databinding.ActivityBateriaBinding

class BateriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBateriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBateriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}














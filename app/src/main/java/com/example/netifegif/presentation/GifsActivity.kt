package com.example.netifegif.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.netifegif.databinding.ActivityMainBinding


class GifsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
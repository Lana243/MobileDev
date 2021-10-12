package com.example.mobileapp

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
}
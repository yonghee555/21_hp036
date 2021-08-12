package com.example.smartsecurevest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartsecurevest.databinding.ActivityHomeBinding
import com.google.android.gms.location.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)    // 뷰바인딩 사용
        val view = binding.root
        setContentView(view)

        binding.mapBtn.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)

        }
        binding.weatherBtn.setOnClickListener{
            val intent = Intent(this, weatherActivity::class.java)
            startActivity(intent)
        }

        binding.workerBtn.setOnClickListener{
            val intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

}
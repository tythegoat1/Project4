package com.example.project4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project4.R.id

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(id.content, LatestMoviesFragment(), null).commit()
    }
}


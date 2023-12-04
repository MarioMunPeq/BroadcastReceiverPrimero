package com.example.broadcastreceiverprimera

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var wifiText: TextView

    private val wifiReceiver = object : BroadcastReceiver() {

        /**
         *
         *
         * @param context
         * @param intent
         */
        override fun onReceive(context: Context?, intent: Intent?) {

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiText = findViewById(R.id.wifiText)

        registerReceiver(wifiReceiver, IntentFilter())


    }
}
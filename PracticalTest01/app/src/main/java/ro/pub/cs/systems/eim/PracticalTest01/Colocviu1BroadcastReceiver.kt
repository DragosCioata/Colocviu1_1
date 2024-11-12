package ro.pub.cs.systems.eim.PracticalTest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Colocviu1BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Extract the data from the intent
        val time = intent?.getStringExtra("TIME")
        val instruction = intent?.getStringExtra("INSTRUCTION")

        // Log the received data to the console
        Log.d("Colocviu1BroadcastReceiver", "Received broadcast message at $time with instruction: $instruction")
    }
}
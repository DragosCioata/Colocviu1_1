package ro.pub.cs.systems.eim.PracticalTest01

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Colocviu1_Service : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Get the instruction from the intent
        val instruction = intent?.getStringExtra("INSTRUCTION") ?: "No instruction"

        // Delay execution by 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Create the broadcast intent
            val broadcastIntent = Intent("ro.pub.cs.systems.eim.PracticalTest01.ACTION_BROADCAST")
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            broadcastIntent.putExtra("TIME", currentTime)
            broadcastIntent.putExtra("INSTRUCTION", instruction)
            sendBroadcast(broadcastIntent) // Send the broadcast
            stopSelf() // Stop the service after sending the broadcast
        }, 5000) // 5-second delay

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // This is a started service, not a bound service
    }
}
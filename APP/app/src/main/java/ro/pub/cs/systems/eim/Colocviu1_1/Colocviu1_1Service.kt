package ro.pub.cs.systems.eim.Colocviu1_1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.*

class Colocviu1_1Service : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Preia instrucțiunile din Intent
        val instructions = intent?.getStringExtra("INSTRUCTIONS") ?: ""

        // Programăm o întârziere de 5 secunde pentru a trimite mesajul cu difuzare
        Handler(Looper.getMainLooper()).postDelayed({
            // Creăm intenția pentru difuzare
            val broadcastIntent = Intent("ro.pub.cs.systems.eim.Colocviu1_1.MESSAGE")
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            broadcastIntent.putExtra("MESSAGE", "Data și ora: $date, Instrucțiuni: $instructions")

            // Trimite mesajul cu difuzare
            sendBroadcast(broadcastIntent)
        }, 5000) // 5 secunde întârziere

        // Continuă rularea serviciului până când acesta este oprit explicit
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Opțional: Adaugă o logică de curățare la oprirea serviciului
    }
}
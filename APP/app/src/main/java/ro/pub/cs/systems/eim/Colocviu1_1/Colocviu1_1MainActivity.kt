package ro.pub.cs.systems.eim.Colocviu1_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.widget.Button
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.content.IntentFilter


class Colocviu1_1MainActivity : AppCompatActivity() {

    private lateinit var textViewOutput: TextView
    private var buttonClickCount = 0 // Variabilă pentru numărul de butoane apăsate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colocviu1_1_main)

        // Inițializează TextView-ul pentru a afișa textul concatenat
        textViewOutput = findViewById(R.id.textView_output)

        // Setează ascultători pentru fiecare buton
        val buttonNorth: Button = findViewById(R.id.button_north)
        val buttonSouth: Button = findViewById(R.id.button_south)
        val buttonEast: Button = findViewById(R.id.button_east)
        val buttonWest: Button = findViewById(R.id.button_west)

        // Ascultător pentru butoanele de direcție
        buttonNorth.setOnClickListener { appendText("North") }
        buttonSouth.setOnClickListener { appendText("South") }
        buttonEast.setOnClickListener { appendText("East") }
        buttonWest.setOnClickListener { appendText("West") }

        // Buton pentru a lansa activitatea secundară
        val buttonLaunchSecondary: Button = findViewById(R.id.button_navigate)
        buttonLaunchSecondary.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("INSTRUCTIONS", textViewOutput.text.toString())
            startActivityForResult(intent, REQUEST_CODE_SECONDARY)
        }

        // Buton pentru a porni serviciul (folosește un ID diferit pentru acest buton)
        val buttonLaunchService: Button = findViewById(R.id.button_start_service)
        buttonLaunchService.setOnClickListener {
            val instructions = textViewOutput.text.toString()
            if (containsAllDirections(instructions)) {
                val intent = Intent(this, Colocviu1_1Service::class.java)
                intent.putExtra("INSTRUCTIONS", instructions)
                startService(intent)
            } else {
                Log.d("Colocviu1_1MainActivity", "Instrucțiunile nu conțin toate direcțiile")
            }
        }
    }

    private fun appendText(direction: String) {
        val currentText = textViewOutput.text.toString()
        val newText = if (currentText.isEmpty()) direction else "$currentText, $direction"
        textViewOutput.text = newText
        buttonClickCount++
        Log.d("ButtonClickCount", "Număr total de apăsări: $buttonClickCount")
    }

    private fun containsAllDirections(text: String): Boolean {
        return text.contains("North") && text.contains("South") &&
                text.contains("East") && text.contains("West")
    }

    // Salvăm atât starea contorului, cât și textul din TextView atunci când activitatea este distrusă temporar
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("buttonClickCount", buttonClickCount)
        outState.putString("textViewOutput", textViewOutput.text.toString())
    }

    // Restaurăm starea contorului și textul din TextView atunci când activitatea este recreată
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        buttonClickCount = savedInstanceState.getInt("buttonClickCount", 0)
        textViewOutput.text = savedInstanceState.getString("textViewOutput", "")
    }

    // Resetăm textul și contorul doar atunci când revenim din SecondaryActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SECONDARY) {
            // Resetăm textul din TextView și contorul
            textViewOutput.text = "" // Resetează textul din TextView
            buttonClickCount = 0 // Resetează contorul
        }
    }

    override fun onResume() {
        super.onResume()
        // Înregistrăm receptorul pentru a primi mesajele difuzate de Colocviu1_1Service
        val intentFilter = IntentFilter("ro.pub.cs.systems.eim.Colocviu1_1.MESSAGE")
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            // Pentru Android 12 și mai sus (API 31+), folosim RECEIVER_NOT_EXPORTED
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            // Pentru versiunile mai vechi, înregistrăm fără flag
            registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        // Deregistrăm receptorul pentru a preveni scurgerile de memorie
        unregisterReceiver(broadcastReceiver)
    }

    // BroadcastReceiver pentru a asculta mesajele de difuzare
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("MESSAGE")
            Log.d("BroadcastReceiver", "Mesaj primit: $message")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Oprește serviciul când activitatea este distrusă
        val intent = Intent(this, Colocviu1_1Service::class.java)
        stopService(intent)
    }

    companion object {
        private const val REQUEST_CODE_SECONDARY = 1
    }
}
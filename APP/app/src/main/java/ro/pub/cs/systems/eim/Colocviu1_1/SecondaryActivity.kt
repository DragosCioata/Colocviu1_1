package ro.pub.cs.systems.eim.Colocviu1_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        // Obține instrucțiunile transmise prin Intent
        val instructions = intent.getStringExtra("INSTRUCTIONS") ?: ""

        // Afișează instrucțiunile în TextView
        val textViewInstructions: TextView = findViewById(R.id.textView_instructions)
        textViewInstructions.text = instructions

        // Configurează butonul Register
        val buttonRegister: Button = findViewById(R.id.button_register)
        buttonRegister.setOnClickListener {
            // Afișează Toast și revine la activitatea principală
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
            finish() // Revine la activitatea principală
        }

        // Configurează butonul Cancel
        val buttonCancel: Button = findViewById(R.id.button_cancel)
        buttonCancel.setOnClickListener {
            // Afișează Toast și revine la activitatea principală
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            finish() // Revine la activitatea principală
        }
    }
}
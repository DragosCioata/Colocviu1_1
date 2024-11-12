package ro.pub.cs.systems.eim.PracticalTest01

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_secondary)

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve and display the sum from intent
        val instructionsTextView = findViewById<TextView>(R.id.instructionsTextView)
        val sum = intent.getIntExtra("SUM", 0)
        instructionsTextView.text = "Sum of button presses: $sum"

        // Set up Register button to send result, show toast, and finish activity
        findViewById<Button>(R.id.registerButton).setOnClickListener {
            Toast.makeText(this, "Register button pressed", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK) // Indicate success
            finish()
        }

        // Set up Cancel button to send result, show toast, and finish activity
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            Toast.makeText(this, "Cancel button pressed", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK) // Indicate success
            finish()
        }
    }
}
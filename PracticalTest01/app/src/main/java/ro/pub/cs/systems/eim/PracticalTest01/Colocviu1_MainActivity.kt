package ro.pub.cs.systems.eim.PracticalTest01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter


class Colocviu1_MainActivity : AppCompatActivity() {
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private val REQUEST_CODE = 1 // Request code for identifying the result
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_main)

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize TextViews and Buttons
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val navigateButton = findViewById<Button>(R.id.navigateButton)

        // Set up button listeners to increment corresponding TextView values
        button1.setOnClickListener {
            val currentCount = textView1.text.toString().toInt()
            textView1.text = (currentCount + 1).toString()
        }

        button2.setOnClickListener {
            val currentCount = textView2.text.toString().toInt()
            textView2.text = (currentCount + 1).toString()
        }

        // Set up navigateButton to launch Secondary Activity with sum of counters
        navigateButton.setOnClickListener {
            val count1 = textView1.text.toString().toInt()
            val count2 = textView2.text.toString().toInt()
            val sum = count1 + count2

            val intent = Intent(this, Colocviu1_SecondaryActivity::class.java)
            intent.putExtra("SUM", sum)

            // Start the service if SUM > 4
            if (sum > 4) {
                val serviceIntent = Intent(this, Colocviu1_Service::class.java)
                serviceIntent.putExtra("INSTRUCTION", "The sum is $sum")
                startService(serviceIntent)
            }

            startActivityForResult(intent, REQUEST_CODE)
        }

        // Restore saved instance state
        if (savedInstanceState != null) {
            textView1.text = savedInstanceState.getString("count1", "0")
            textView2.text = savedInstanceState.getString("count2", "0")
        }

        // Initialize and register the broadcast receiver
        broadcastReceiver = Colocviu1BroadcastReceiver()
        val intentFilter = IntentFilter("ro.pub.cs.systems.eim.PracticalTest01.ACTION_BROADCAST")
        registerReceiver(broadcastReceiver, intentFilter)
    }

    // Handle result from Secondary Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Reset counters
            textView1.text = "0"
            textView2.text = "0"
        }
    }

    // Save instance state to preserve counter values
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the current values of the text views
        outState.putString("count1", textView1.text.toString())
        outState.putString("count2", textView2.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore the values of the text views
        textView1.text = savedInstanceState.getString("count1", "0")
        textView2.text = savedInstanceState.getString("count2", "0")
    }

    // Unregister the broadcast receiver to prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

}
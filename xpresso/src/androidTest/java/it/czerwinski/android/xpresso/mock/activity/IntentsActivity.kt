package it.czerwinski.android.xpresso.mock.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import it.czerwinski.android.xpresso.test.R

class IntentsActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById<TextView>(R.id.text)
    }

    private val button: Button by lazy {
        findViewById<Button>(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.intents_activity)

        textView.text = intent.getStringExtra("extra")

        button.setOnClickListener {
            startActivityForResult(Intent(), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        textView.text = "$resultCode | ${data?.getStringExtra("extra")}"
    }
}

package com.example.autorentmobile

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val title =
            findViewById<TextView>(R.id.textTitle)

        val year =
            findViewById<TextView>(R.id.textYear)

        val price =
            findViewById<TextView>(R.id.textPrice)

        val button =
            findViewById<Button>(R.id.buttonBook)

        val brand =
            intent.getStringExtra("brand")

        val model =
            intent.getStringExtra("model")

        val carYear =
            intent.getIntExtra("year", 0)

        val carPrice =
            intent.getDoubleExtra("price", 0.0)

        title.text = "$brand $model"

        year.text = "Year: $carYear"

        price.text = "Price per day: $carPrice$"

        button.setOnClickListener {

            Toast.makeText(
                this,
                "Car booked successfully!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
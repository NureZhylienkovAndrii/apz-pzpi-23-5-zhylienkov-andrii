package com.example.autorentmobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewCars)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        loadCars()
    }

    private fun loadCars() {

        RetrofitClient.api.getCars()
            .enqueue(object : Callback<List<Car>> {

                override fun onResponse(
                    call: Call<List<Car>>,
                    response: Response<List<Car>>
                ) {

                    if (response.isSuccessful) {

                        val cars = response.body() ?: emptyList()

                        recyclerView.adapter =
                            CarAdapter(cars)
                    }
                }

                override fun onFailure(
                    call: Call<List<Car>>,
                    t: Throwable
                ) {

                    t.printStackTrace()

                    Toast.makeText(
                        this@MainActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
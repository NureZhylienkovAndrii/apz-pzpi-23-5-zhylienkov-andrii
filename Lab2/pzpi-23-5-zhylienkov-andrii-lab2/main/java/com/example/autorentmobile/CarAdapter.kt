package com.example.autorentmobile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(
    private val cars: List<Car>
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val brand: TextView =
            view.findViewById(R.id.textBrand)

        val year: TextView =
            view.findViewById(R.id.textYear)

        val price: TextView =
            view.findViewById(R.id.textPrice)

        val button: Button =
            view.findViewById(R.id.buttonDetails)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car, parent, false)

        return CarViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CarViewHolder,
        position: Int
    ) {

        val car = cars[position]

        holder.brand.text =
            "${car.brand} ${car.model}"

        holder.year.text =
            "Year: ${car.year}"

        holder.price.text =
            "Price per day: ${car.pricePerDay}$"

        holder.button.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                DetailActivity::class.java
            )

            intent.putExtra("brand", car.brand)
            intent.putExtra("model", car.model)
            intent.putExtra("year", car.year)
            intent.putExtra("price", car.pricePerDay)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = cars.size
}
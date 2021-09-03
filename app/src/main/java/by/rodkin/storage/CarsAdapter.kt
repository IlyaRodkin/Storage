package by.rodkin.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.rodkin.storage.databinding.ItemCarBinding

class CarsAdapter(private val listCars: List<Car>) : RecyclerView.Adapter<CarsAdapter.CarsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarBinding.inflate(inflater, parent, false)
        return CarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = listCars[position]
        try {
            with(holder.binding){
                /*if(car.icon != null)  carIcon.setImageDrawable(car.icon)
                else */
                carIcon.setImageResource(R.drawable.ic_baseline_directions_car)
                carLabel.text = car.model
                carYear.text = car.year.toString()
                carPrice.text = "${car.price}$"
            }
        }catch (e: Exception){

        }

    }

    override fun getItemCount(): Int = listCars.size

    class CarsViewHolder(
        val binding: ItemCarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}
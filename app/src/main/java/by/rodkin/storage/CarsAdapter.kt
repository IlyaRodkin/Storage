package by.rodkin.storage

import android.net.wifi.p2p.WifiP2pManager
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import by.rodkin.storage.databinding.ItemCarBinding
import java.nio.file.Files.delete

interface OnClickMoreButton{
    fun onCarUpdate(car: Car)
    fun onCarDelete(car: Car)
}

class CarsAdapter(private val listCars: List<Car>,
private val actionListener: OnClickMoreButton) : RecyclerView.Adapter<CarsAdapter.CarsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarBinding.inflate(inflater, parent, false)

        binding.moreImageViewButton.setOnClickListener { showPopupMenu(it) }

        return CarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = listCars[position]
        holder.binding.moreImageViewButton.tag = car
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

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val car = view.tag as Car

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_DELETE, Menu.NONE, context.getString(R.string.delete))


        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onCarUpdate(car)
                }
                ID_DELETE -> {
                    actionListener.onCarDelete(car)
                }

            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun getItemCount(): Int = listCars.size

    class CarsViewHolder(
        val binding: ItemCarBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object{
        private const val ID_UPDATE = 1
        private const val ID_DELETE = 2
    }
}
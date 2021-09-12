package by.rodkin.storage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.rodkin.storage.dbRoom.Car
import by.rodkin.storage.CarsAdapter
import by.rodkin.storage.OnClickMoreButton
import by.rodkin.storage.databinding.FragmentCarsListBinding

class CarListFragment: Fragment() {

    private lateinit var binding: FragmentCarsListBinding
    private lateinit var cars: ArrayList<Car>
    private lateinit var listener: OnActionAddCar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = requireContext() as OnActionAddCar

        cars = if(savedInstanceState != null) savedInstanceState.get(KEY_CARS) as ArrayList<Car>
        else if (arguments != null) arguments?.get(ARG_CARS) as ArrayList<Car>
        else throw IllegalAccessException("Can't get list of cars")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarsListBinding.inflate(inflater, container, false)
        binding.recyclerViewCars.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CarsAdapter(cars,
            object : OnClickMoreButton {
                override fun onCarUpdate(car: Car) {
                    listener.onCarUpdate(car)
                }

                override fun onCarDelete(car: Car) {
                    listener.onCarDelete(car)
                }

            })
        }

        binding.fabAdd.setOnClickListener {
            onAddCar()
        }

        return binding.root
    }

    private fun onAddCar() {
        listener.addToCarList(null)
    }

    companion object {

        fun newInstance(cars: List<Car>): CarListFragment {
            val fragment = CarListFragment()
            fragment.arguments = bundleOf(ARG_CARS to cars)
            return fragment
        }

        private const val ARG_CARS = "arg_cars"
        private const val KEY_CARS = "key_cars"
    }

    interface OnActionAddCar{
        fun addToCarList(car: Car?)
        fun onCarUpdate(car: Car)
        fun onCarDelete(car: Car)
    }
}
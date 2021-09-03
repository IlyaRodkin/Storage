package by.rodkin.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.rodkin.storage.databinding.FragmentCarsListBinding

class CarListFragment: Fragment() {

    private lateinit var binding: FragmentCarsListBinding
    private lateinit var cars: ArrayList<Car>
    private lateinit var listener: OnActionAddCar

    //private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.) }

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
            adapter = CarsAdapter(cars)
        }

        binding.fabAdd.setOnClickListener {
            onAddCar()
        }

        return binding.root
    }

    private fun onAddCar() {
        listener.addToCarList()
    }

    companion object {

        fun newInstance(cars: ArrayList<Car>): CarListFragment {
            val fragment = CarListFragment()
            fragment.arguments = bundleOf(ARG_CARS to cars)
            return fragment
        }

        private const val ARG_CARS = "arg_cars"
        private const val KEY_CARS = "key_cars"
    }

    interface OnActionAddCar{
        fun addToCarList()
    }
}
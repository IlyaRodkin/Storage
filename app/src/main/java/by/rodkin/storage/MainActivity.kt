package by.rodkin.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.rodkin.storage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),CarListFragment.OnActionAddCar {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //databaseHelper = DatabaseHelper(applicationContext)
        openRecyclerView(cars)
    }

    override fun onResume() {
        super.onResume()
       // db = databaseHelper.readableDatabase

        //carCursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE}", null)


    }

    private fun openRecyclerView(carsList: ArrayList<Car>) {
        val fragment = CarListFragment.newInstance(carsList)
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun addToCarList() {
        AddCarDialogFragment().show(supportFragmentManager, AddCarDialogFragment.TAG)
    }

    companion object {
        val cars = arrayListOf(
            Car(0, "toyota", 2009, 10000),
            Car(1, "rover", 2019, 13200),
            Car(2, "saab", 2014, 15000),
            Car(3, "skoda", 2005, 3800),
            Car(4, "subaru", 2003, 9200),
            Car(5, "suzuki", 2012, 8500),
            Car(0, "toyota", 2009, 10000),
            Car(1, "rover", 2019, 13200),
            Car(2, "saab", 2014, 15000),
            Car(3, "skoda", 2005, 3800),
            Car(4, "subaru", 2003, 9200),
            Car(5, "suzuki", 2012, 8500),

            )
    }
}
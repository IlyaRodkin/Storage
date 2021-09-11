package by.rodkin.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import by.rodkin.storage.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity(),CarListFragment.OnActionAddCar {

    private lateinit var db: AppDatabase

    private var cars = emptyList<Car>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = App.instance?.getDatabase() ?: throw Exception("error")
        updateUI()
        setupAddCarToDbListener()
        setupFilterModeListener()
        //databaseHelper = DatabaseHelper(applicationContext)

    }

    fun updateUI() {
        if(cars != db.carDao().getAll() && cars.size != db.carDao().getAll().size)
        cars = db.carDao().getAll()
        openRecyclerView(cars)

        //carCursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE}", null)
    }

    private fun openRecyclerView(carsList: List<Car>) {
        val fragment = CarListFragment.newInstance(carsList)
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun addToCarList() {
        AddCarDialogFragment().show(supportFragmentManager, AddCarDialogFragment.TAG)
    }

    private fun setupAddCarToDbListener(){
        AddCarDialogFragment.setupListener(supportFragmentManager, this){
            db.carDao().insert(it as Car)
            updateUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        FilterCarsDialogFragment().show(supportFragmentManager,FilterCarsDialogFragment.TAG)
        return true
    }

    private fun setupFilterModeListener(){
        FilterCarsDialogFragment.setupListener(supportFragmentManager,this){
             when(it) {
                FilterCarsDialogFragment.SORT_BY_MODEL ->  cars = db.carDao().sortedByModel()
                FilterCarsDialogFragment.SORT_BY_YEAR ->  cars = db.carDao().sortedByYear()
                FilterCarsDialogFragment.SORT_BY_PRICE ->  cars = db.carDao().sortedByPrice()
            }
                updateUI()
        }
    }

}
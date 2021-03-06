package by.rodkin.storage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.rodkin.storage.databinding.ActivityMainBinding
import by.rodkin.storage.dbRoom.AppDatabase
import by.rodkin.storage.dbRoom.Car
import by.rodkin.storage.fragments.AddCarDialogFragment
import by.rodkin.storage.fragments.CarListFragment
import by.rodkin.storage.fragments.FilterCarsDialogFragment

class MainActivity : AppCompatActivity(), CarListFragment.OnActionAddCar {

    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var db: AppDatabase

    private var cars = emptyList<Car>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        db = App.instance?.getDatabase() ?: throw Exception("error")
        updateUI()
        setupAddCarToDbListener()
        setupFilterModeListener()
        //databaseHelper = DatabaseHelper(applicationContext)

    }

    fun updateUI() {
        if (cars != db.carDao().getAll() && cars.size != db.carDao().getAll().size)
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

    override fun addToCarList(car: Car?) {
        AddCarDialogFragment.show(supportFragmentManager, car)
    }

    override fun onCarUpdate(car: Car) {
        addToCarList(car)
    }

    override fun onCarDelete(car: Car) {
        db.carDao().delete(car)
        updateUI()
    }

    private fun setupAddCarToDbListener() {
        AddCarDialogFragment.setupListener(supportFragmentManager, this) {
            val car = it as Car
            if (car.id != null)
                db.carDao().update(car)
            else
                db.carDao().insert(car)

            updateUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        FilterCarsDialogFragment().show(supportFragmentManager, FilterCarsDialogFragment.TAG)
        return true
    }

    private fun setupFilterModeListener() {
        FilterCarsDialogFragment.setupListener(supportFragmentManager, this) {
            when (it) {
                FilterCarsDialogFragment.SORT_BY_MODEL -> cars = db.carDao().sortedByModel()
                FilterCarsDialogFragment.SORT_BY_YEAR -> cars = db.carDao().sortedByYear()
                FilterCarsDialogFragment.SORT_BY_PRICE -> cars = db.carDao().sortedByPrice()
            }
            updateUI()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
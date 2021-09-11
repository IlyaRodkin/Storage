package by.rodkin.storage

import androidx.room.*


@Dao
interface CarDao {

    @Query("SELECT * FROM car")
    fun getAll(): List<Car>

    @Query("SELECT * FROM car WHERE id = :id")
    fun getById(id: Long): Car?

    @Insert
    fun insert(car: Car?)

    @Update
    fun update(car: Car?)

    @Delete
    fun delete(car: Car?)

    @Query("SELECT * FROM car ORDER BY model")
    fun sortedByModel() : List<Car>

    @Query("SELECT * FROM car ORDER BY year")
    fun sortedByYear() : List<Car>

    @Query("SELECT * FROM car ORDER BY price")
    fun sortedByPrice() : List<Car>
}
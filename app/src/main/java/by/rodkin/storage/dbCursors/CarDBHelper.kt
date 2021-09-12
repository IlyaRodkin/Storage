package by.rodkin.storage.dbCursors

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import by.rodkin.storage.dbRoom.Car
import java.util.ArrayList

class CarDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(car: Car): Boolean {
        val db = writableDatabase

        val carContent = ContentValues()
        carContent.put(DBCar.CarEntry.COLUMN_CAR_ID, car.id)
        carContent.put(DBCar.CarEntry.COLUMN_MODEL, car.model)
        carContent.put(DBCar.CarEntry.COLUMN_YEAR, car.year)
        carContent.put(DBCar.CarEntry.COLUMN_PRICE, car.price)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCar(carId: Int): Boolean {
        val db = writableDatabase

        db.delete(DBCar.CarEntry.TABLE_NAME,DBCar.CarEntry.COLUMN_CAR_ID + "=" + carId, null)

        return true
    }

    fun readAllUsers(): ArrayList<Car> {
        val cars = ArrayList<Car>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBCar.CarEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var carId: Int
        var model: String
        var year: Int
        var price: Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {

                model = cursor.getString(cursor.getColumnIndex(DBCar.CarEntry.COLUMN_MODEL))
                year = cursor.getInt(cursor.getColumnIndex(DBCar.CarEntry.COLUMN_YEAR))
                price = cursor.getInt(cursor.getColumnIndex(DBCar.CarEntry.COLUMN_PRICE))
                carId = cursor.getInt(cursor.getColumnIndex(DBCar.CarEntry.COLUMN_CAR_ID))

                cars.add(Car(model, year, price, carId))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return cars
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "car_table.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBCar.CarEntry.TABLE_NAME + " (" +
                    DBCar.CarEntry.COLUMN_CAR_ID + " INT PRIMARY KEY AUTO_INCREMENT," +
                    DBCar.CarEntry.COLUMN_MODEL + " TEXT," +
                    DBCar.CarEntry.COLUMN_YEAR + " INT," +
                    DBCar.CarEntry.COLUMN_PRICE + " INT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBCar.CarEntry.TABLE_NAME
    }

}
package by.rodkin.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Car::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun carDao(): CarDao
}
package by.rodkin.storage.dbCursors

import android.provider.BaseColumns

object DBCar {

    class CarEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "players_table"
            val COLUMN_CAR_ID = "id"
            val COLUMN_MODEL = "model"
            val COLUMN_YEAR = "year"
            val COLUMN_PRICE = "price"
        }
    }
}
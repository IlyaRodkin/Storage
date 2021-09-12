package by.rodkin.storage

import  android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Car(
    //val icon: Drawable?,
    var model: String,
    var year: Int,
    var price: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
): Parcelable
package by.rodkin.storage

import  android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Car(
    //val icon: Drawable?,
    val model: String,
    val year: Int,
    val price: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
): Parcelable
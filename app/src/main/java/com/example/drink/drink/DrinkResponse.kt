package com.example.drink.drink

import android.os.Parcel
import android.os.Parcelable

data class DrinkResponse(
    val results: ArrayList<ResultDrink>?,
    val status: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createTypedArrayList(ResultDrink.CREATOR),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(results)
        writeInt((if (status) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DrinkResponse> = object : Parcelable.Creator<DrinkResponse> {
            override fun createFromParcel(source: Parcel): DrinkResponse = DrinkResponse(source)
            override fun newArray(size: Int): Array<DrinkResponse?> = arrayOfNulls(size)
        }
    }
}

data class ResultDrink(
    val drink_id: Int,
    val drink_image: String?,
    val drink_name: String?,
    val drink_price: Float,
    val menu_id: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readFloat(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(drink_id)
        writeString(drink_image)
        writeString(drink_name)
        writeFloat(drink_price)
        writeInt(menu_id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ResultDrink> = object : Parcelable.Creator<ResultDrink> {
            override fun createFromParcel(source: Parcel): ResultDrink = ResultDrink(source)
            override fun newArray(size: Int): Array<ResultDrink?> = arrayOfNulls(size)
        }
    }
}
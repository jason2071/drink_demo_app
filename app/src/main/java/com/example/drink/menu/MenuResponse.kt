package com.example.drink.menu

import android.os.Parcel
import android.os.Parcelable

data class MenuResponse(
    val results: ArrayList<ResultMenu>?,
    val status: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createTypedArrayList(ResultMenu.CREATOR),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(results)
        writeInt((if (status) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MenuResponse> = object : Parcelable.Creator<MenuResponse> {
            override fun createFromParcel(source: Parcel): MenuResponse = MenuResponse(source)
            override fun newArray(size: Int): Array<MenuResponse?> = arrayOfNulls(size)
        }
    }
}

data class ResultMenu(
    val menu_id: Int,
    val menu_image: String?,
    val menu_name: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(menu_id)
        writeString(menu_image)
        writeString(menu_name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ResultMenu> = object : Parcelable.Creator<ResultMenu> {
            override fun createFromParcel(source: Parcel): ResultMenu = ResultMenu(source)
            override fun newArray(size: Int): Array<ResultMenu?> = arrayOfNulls(size)
        }
    }
}
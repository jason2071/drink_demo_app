package com.example.drink.register

import android.os.Parcel
import android.os.Parcelable

data class UserResponse(
    val status: Boolean
    , val id: Int
    , val phone: String?
    , val name: String?
    , val address: String?
    , val birthday: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        1 == source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (status) 1 else 0))
        writeInt(id)
        writeString(phone)
        writeString(name)
        writeString(address)
        writeString(birthday)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserResponse> = object : Parcelable.Creator<UserResponse> {
            override fun createFromParcel(source: Parcel): UserResponse = UserResponse(source)
            override fun newArray(size: Int): Array<UserResponse?> = arrayOfNulls(size)
        }
    }
}
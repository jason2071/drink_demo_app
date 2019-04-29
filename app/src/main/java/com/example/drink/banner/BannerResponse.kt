package com.example.drink.banner

import android.os.Parcel
import android.os.Parcelable

data class BannerResponse(
    val results: ArrayList<ResultBanner>?,
    val status: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createTypedArrayList(ResultBanner.CREATOR),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(results)
        writeInt((if (status) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BannerResponse> = object : Parcelable.Creator<BannerResponse> {
            override fun createFromParcel(source: Parcel): BannerResponse =
                BannerResponse(source)
            override fun newArray(size: Int): Array<BannerResponse?> = arrayOfNulls(size)
        }
    }
}

data class ResultBanner(
    val banner_id: Int,
    val banner_image: String?,
    val banner_name: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(banner_id)
        writeString(banner_image)
        writeString(banner_name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ResultBanner> = object : Parcelable.Creator<ResultBanner> {
            override fun createFromParcel(source: Parcel): ResultBanner =
                ResultBanner(source)
            override fun newArray(size: Int): Array<ResultBanner?> = arrayOfNulls(size)
        }
    }
}
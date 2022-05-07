package com.Mandeep.ventyvideoplayer.MVVM

import android.os.Parcel
import android.os.Parcelable

data class MediaClasss(
    var artistName: String?,
    var songName:String?,
    val bucket:String,
    val relativePath:String,
    var link:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(artistName)
        parcel.writeString(songName)
        parcel.writeString(bucket)
        parcel.writeString(relativePath)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaClasss> {
        override fun createFromParcel(parcel: Parcel): MediaClasss {
            return MediaClasss(parcel)
        }

        override fun newArray(size: Int): Array<MediaClasss?> {
            return arrayOfNulls(size)
        }
    }
}

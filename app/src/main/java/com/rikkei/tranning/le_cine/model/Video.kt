package com.rikkei.tranning.le_cine.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rikkei.tranning.le_cine.util.EMPTY

data class Video(
    var id: String,
    var name: String,
    var site: String,
    @SerializedName("key") var videoId: String,
    var size: Int,
    var type: String
) : Parcelable {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(site)
        parcel.writeString(videoId)
        parcel.writeInt(size)
        parcel.writeString(type)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Video> {

        const val SITE_YOUTUBE = "YouTube"
        private const val YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%1\$s"
        private const val YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1\$s/0.jpg"

        fun getUrl(video: Video): String {
            return if (SITE_YOUTUBE.equals(video.site, ignoreCase = true)) {
                String.format(YOUTUBE_VIDEO_URL, video.videoId)
            } else {
                EMPTY
            }
        }

        fun getThumbnailUrl(video: Video): String {
            return if (SITE_YOUTUBE.equals(video.site, ignoreCase = true)) {
                String.format(YOUTUBE_THUMBNAIL_URL, video.videoId)
            } else {
                EMPTY
            }
        }

        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }
}
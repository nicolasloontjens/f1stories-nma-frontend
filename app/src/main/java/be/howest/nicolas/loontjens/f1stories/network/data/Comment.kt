package be.howest.nicolas.loontjens.f1stories.network.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val commentid: Int,
    val userid: Int,
    val storyid: Int,
    val content: String,
    val username: String
):Parcelable

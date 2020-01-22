package net.d3b8g.music_urselfs_list.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicParcel(
             var name:String,
             var creator:String,
             var isMine:Boolean ,
             var time: Int ,
             var music_id: Int,
             var avatar: String
):Parcelable
package net.d3b8g.music_urselfs_list.Work

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun unixtime(time:Long, format:String) = SimpleDateFormat(format).format(Date(time*1000))
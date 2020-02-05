package net.d3b8g.music_urselfs_list.Shared

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


/*
 0 - Boolean [0: name of shared_name; 1:param_of_put; 2:name of file; 3: default value / set  value/]
 1 - String
 2 - Integer
            var param:ArrayList<Any> = ArrayList()
            param.add(Data.shared_name)
            param.add(0)
            param.add(is_saved)
            param.add(false)
*/

suspend fun set_data(ct:Context,param:ArrayList<Any>) = withContext(IO){
    val pref = ct.getSharedPreferences(param[0].toString(), MODE_PRIVATE)
    val set = pref.edit()
    when(param[1]){
        0->{set.putBoolean(param[2].toString(), param[3] as Boolean).apply()}
        1->{set.putString(param[2].toString(), param[3] as String).apply()}
        2->{set.putInt(param[3].toString(), param[3] as Int).apply()}
        else->{set.putString("error","error_on ${param[3]}")}
    }
}
suspend fun get_data(ct:Context,param:ArrayList<Any>) = withContext(IO){
    val pref = ct.getSharedPreferences(param[0].toString(), MODE_PRIVATE)
    return@withContext when(param[1]){
        0->{pref.getBoolean(param[2].toString(),param[3] as Boolean)}
        1->{pref.getString(param[2].toString(),param[3] as String)}
        2->{pref.getInt(param[2].toString(),param[3] as Int)}
        else->{"error on ${param[3]}"}
    }
}
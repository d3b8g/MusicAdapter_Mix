package net.d3b8g.music_urselfs_list.SERVER_FAKE

import android.util.Log
import com.google.gson.JsonParser
import net.d3b8g.music_urselfs_list.Model.MusicParcel
import kotlin.random.Random
import kotlin.random.nextInt

class MusicParamString{

    fun getMusicFromServer():String {

        var musicArrayList:ArrayList<String> = ArrayList()

        var url_music:ArrayList<String> = arrayListOf("https://sun9-20.userapi.com/c850616/v850616064/171e6d/RchYWdzSVNQ.jpg","https://sun9-9.userapi.com/c855136/v855136841/19fef9/IPBYNCcHEgI.jpg","https://sun9-62.userapi.com/c847220/v847220646/1cf7d5/JZxBwMAs9DQ.jpg",
            "https://sun9-24.userapi.com/c858036/v858036485/ffc1a/hfFcCvGC2FQ.jpg","https://sun9-64.userapi.com/c200828/v200828582/1d35f/ee_4KJxMeP4.jpg")

        for( x in Random.nextInt(0..3)..Random.nextInt(7..15)){
            musicArrayList.add(
                "\"name\" : \"MusicScott${Random.nextInt(0..3)}xxFeat${Random.nextInt(4..9)}\"," +
                        "\"creator\":\"ScotCreator_${Random.nextInt(7..16)}\","+
                        "\"isMine\":${Random.nextInt(0..1)==1},"+
                        "\"time\": ${(200..315).random()},"+
                        "\"music_id\":${x},"+
                        "\"avatar\":\"${url_music[(0 until url_music.size).random()]}\""

            )
        }
        var return_string:String = "{\"response\":["
        for( z in 0 until  musicArrayList.size){
            return_string+="{${musicArrayList[z]}}"
            return_string += if(z!=musicArrayList.size-1) ","
            else "]}"
        }

        return return_string
    }

    fun musicListRecording(result:String):ArrayList<MusicParcel>{
        val parser = JsonParser()
        Log.d("RRR", result)
        val history = parser.parse(result).asJsonObject
        var musicList:ArrayList<MusicParcel> = ArrayList()
        history.getAsJsonArray("response").forEach { _m ->
            val new_item = MusicParcel(
                name = _m.asJsonObject.get("name").asString,
                creator = _m.asJsonObject.get("creator").asString,
                isMine = _m.asJsonObject.get("isMine").asBoolean,
                time = _m.asJsonObject.get("time").asInt,
                music_id = _m.asJsonObject.get("music_id").asInt,
                avatar = _m.asJsonObject.get("avatar").asString
            )
            musicList.add(new_item)
        }
        return musicList
    }
}

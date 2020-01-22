package net.d3b8g.music_urselfs_list.Shared

import net.d3b8g.music_urselfs_list.Model.MusicParcel

class Data {
    companion object{
        const val shared_name = "SettingsApp"
        const val is_saved = "IS_MUSIC_SAVED"
        const val check_music = "CheckMusicId"
        const val lmusic_id = "LastMusic"

        var massiv:ArrayList<MusicParcel> = ArrayList()

        var player:Boolean = false
    }
}
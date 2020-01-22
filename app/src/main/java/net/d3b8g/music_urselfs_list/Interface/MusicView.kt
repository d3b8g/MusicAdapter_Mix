package net.d3b8g.music_urselfs_list.Interface

import net.d3b8g.music_urselfs_list.Model.MusicParcel


interface MusicView  {
    fun loadMusicComponents(musicList:ArrayList<MusicParcel>)
    fun startLoadMusic()
    fun errorMessage()
    fun endLoadMusic()
}
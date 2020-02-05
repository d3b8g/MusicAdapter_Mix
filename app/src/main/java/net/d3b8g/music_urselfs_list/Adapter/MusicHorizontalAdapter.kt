package net.d3b8g.music_urselfs_list.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.d3b8g.music_urselfs_list.R

class MusicHorizontalAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var musicList:ArrayList<String> = ArrayList()

    fun update(list:ArrayList<String>,position: Int){
        musicList.clear()
        musicList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_avatar_music,parent,false)
        return MusicViewHorizontalHolder(itemView)
    }

    override fun getItemCount(): Int {
        return musicList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MusicViewHorizontalHolder) {
            holder.bind(musicList[position])
        }
    }

    class MusicViewHorizontalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img:ImageView = itemView.findViewById(R.id.p_img_music)

        fun bind(avatar:String){

            Picasso.get().load(avatar).into(img)

            var shake:Animation = AnimationUtils.loadAnimation(itemView.context,R.anim.shake_image)
            img.animation = shake
        }
    }


}
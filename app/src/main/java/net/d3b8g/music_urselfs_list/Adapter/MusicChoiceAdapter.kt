package net.d3b8g.music_urselfs_list.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.d3b8g.music_urselfs_list.Model.MusicParcel
import net.d3b8g.music_urselfs_list.R

class MusicChoiceAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var musicList:ArrayList<MusicParcel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_music,parent,false)
        return MusicChoiceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MusicChoiceViewHolder) holder.bind(musicList[position])
    }

    class MusicChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var context: Context = itemView.context

        val moretab = itemView.findViewById<ImageButton>(R.id.more_tab)
        val cell_img = itemView.findViewById<ImageView>(R.id.cell_img)
        val cell_label = itemView.findViewById<TextView>(R.id.music_label)
        val cell_creator = itemView.findViewById<TextView>(R.id.music_creator)

        fun bind(item:MusicParcel){

            Picasso.get().load(item.avatar).into(cell_img)
            cell_creator.text = item.creator
            cell_label.text = item.name

        }
    }

}
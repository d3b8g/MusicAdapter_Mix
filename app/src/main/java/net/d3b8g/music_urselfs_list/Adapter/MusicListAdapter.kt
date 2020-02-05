package net.d3b8g.music_urselfs_list.Adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.d3b8g.music_urselfs_list.Activity.MediaPlayer
import net.d3b8g.music_urselfs_list.Model.MusicParcel
import net.d3b8g.music_urselfs_list.R
import net.d3b8g.music_urselfs_list.Shared.Data.Companion.massiv


class MusicListAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var musicList:ArrayList<MusicParcel> = ArrayList()

    fun update(list:ArrayList<MusicParcel>){
        musicList.clear()
        musicList.addAll(list)
        massiv.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_music,parent,false)
        return MusicViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MusicViewHolder) holder.bind(musicList[position])
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var context:Context = itemView.context

        val moretab = itemView.findViewById<ImageButton>(R.id.more_tab)
        val cell_img = itemView.findViewById<ImageView>(R.id.cell_img)
        val cell_label = itemView.findViewById<TextView>(R.id.music_label)
        val cell_creator = itemView.findViewById<TextView>(R.id.music_creator)

        fun bind(item:MusicParcel){

            Picasso.get().load(item.avatar).into(cell_img)
            cell_creator.text = item.creator
            cell_label.text = item.name

            moretab.setOnClickListener {
                val popup_style = ContextThemeWrapper(context,R.style.popup_menu_style_baby)
                val popup = PopupMenu(popup_style,moretab)
                popup.menuInflater.inflate(R.menu.music_menu,popup.menu)
                popup.show()
            }
            itemView.setOnClickListener {
                val myactivity = Intent(context.applicationContext, MediaPlayer::class.java)
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK)
                myactivity.putExtra("id_music",(layoutPosition)+1)
                context.applicationContext.startActivity(myactivity)
            }

        }

    }

}
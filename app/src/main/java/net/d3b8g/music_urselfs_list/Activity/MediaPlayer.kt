package net.d3b8g.music_urselfs_list.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.d3b8g.music_urselfs_list.Adapter.MusicHorizontalAdapter
import net.d3b8g.music_urselfs_list.Model.MusicParcel
import net.d3b8g.music_urselfs_list.R
import net.d3b8g.music_urselfs_list.Shared.Data
import net.d3b8g.music_urselfs_list.Shared.Data.Companion.massiv
import net.d3b8g.music_urselfs_list.Shared.get_data
import kotlin.coroutines.CoroutineContext

class MediaPlayer:AppCompatActivity(), CoroutineScope, View.OnClickListener {

    lateinit var music_list: RecyclerView
    lateinit var name: TextView
    lateinit var creator: TextView
    lateinit var btn_mine: ImageButton
    lateinit var btn_downloaded: ImageButton
    lateinit var btn_repeat: ImageButton
    lateinit var btn_skip_previous: ImageButton
    lateinit var btn_skip_next: ImageButton
    lateinit var btn_start: ImageView
    lateinit var btn_sort: ImageView
    lateinit var linearLayout:LinearLayout

    lateinit var close_payer:ImageButton

    var music_data:ArrayList<MusicParcel> = massiv
    lateinit var adapter:MusicHorizontalAdapter
    var id_now = 0
    var size_m:Int = music_data.size

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    //Metadata
    var timeline:Int = 0
    var isPlaying:Boolean = false
    var isDownloaded:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        adapter = MusicHorizontalAdapter()

        music_list = findViewById(R.id.p_list)

        id_now = intent.getIntExtra("id_music",0) -1
        if(id_now<0)id_now=0


        Log.e("RRR","$size_m  вв $id_now")

        music_list.adapter = adapter
        music_list.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL,false)
        music_list.setHasFixedSize(true)

        adapter.update(updateAvatarUrl(),id_now)

        name = findViewById(R.id.p_music_name)
        creator = findViewById(R.id.p_music_creator)
        btn_downloaded = findViewById(R.id.p_is_download)
        btn_mine = findViewById(R.id.p_have_music)
        btn_repeat = findViewById(R.id.btn_repeat)
        btn_skip_next = findViewById(R.id.btn_next)
        btn_skip_previous = findViewById(R.id.btn_previous)
        btn_start = findViewById(R.id.btn_play)
        btn_sort = findViewById(R.id.btn_sort)
        linearLayout = findViewById(R.id.container_of_avatar)
        close_payer = findViewById(R.id.p_hide)

        close_payer.setOnClickListener(this)
        btn_sort.setOnClickListener(this)
        btn_start.setOnClickListener(this)
        btn_skip_previous.setOnClickListener(this)
        btn_skip_next.setOnClickListener(this)
        btn_repeat.setOnClickListener(this)
        btn_mine.setOnClickListener(this)
        btn_downloaded.setOnClickListener(this)

        setUpData()
    }

    private fun setUpData() {
        name.text = music_data[id_now].name
        creator.text = music_data[id_now].creator
        adapter.update(updateAvatarUrl(),id_now)
    }

    private fun updateAvatarUrl(): ArrayList<String> {
        var result:ArrayList<String> = ArrayList()
        for(i in music_data){
            result.add(i.avatar)
        }
        return result
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_sort->{
                Toast.makeText(this,"in future popup",Toast.LENGTH_LONG).show()
            }
            R.id.btn_next->{
                if(id_now!=size_m-1) {
                    id_now++
                    setUpData()
                }
            }
            R.id.btn_previous->{
                if(id_now!=0) {
                    id_now--
                    setUpData()
                }
            }
            R.id.btn_repeat->{
                id_now=-1
            }
            R.id.btn_play->{
                if(isPlaying){
                    btn_start.setImageResource(R.drawable.ic_pause)
                }else{
                    btn_start.setImageResource(R.drawable.ic_play)
                }
            }
            R.id.p_is_download->{
                launch {
                    if(get_data(this@MediaPlayer, arrayListOf(Data.check_music,0,music_data[id_now].music_id,false)) as Boolean)
                        btn_downloaded.setImageResource(R.drawable.ic_done)
                    else
                        btn_downloaded.setImageResource(R.drawable.ic_file_download)
                }
            }
            R.id.p_have_music->{
                if(music_data[id_now].isMine)
                    btn_mine.setImageResource(R.drawable.ic_done)
                else
                    btn_mine.setImageResource(R.drawable.ic_add)
            }
            R.id.p_hide->{
                finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(isFinishing){
            overridePendingTransition(R.anim.swipe_up_activity,R.anim.swipe_down_activity)
        }
    }

}

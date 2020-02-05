package net.d3b8g.music_urselfs_list.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
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
    lateinit var progress_horizontal:ProgressBar
    lateinit var tEnd:TextView
    lateinit var tStart:TextView

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
    var isPlaying:Boolean = true
    var isDownloaded:Boolean = false
    lateinit var CountDownTimer:CountDownTimer

    var displayWidth = 0
    var counterMillis = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        adapter = MusicHorizontalAdapter()

        music_list = findViewById(R.id.p_list)

        id_now = intent.getIntExtra("id_music",0) -1
        if(id_now<0)id_now=0
        timeline = music_data[id_now].time*1000

        music_list.adapter = adapter
        music_list.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL,false)
        music_list.setHasFixedSize(true)

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
        progress_horizontal = findViewById(R.id.progress_horizontal)
        tStart = findViewById(R.id.time_start)
        tEnd = findViewById(R.id.time_end)

        close_payer.setOnClickListener(this)
        btn_sort.setOnClickListener(this)
        btn_start.setOnClickListener(this)
        btn_skip_previous.setOnClickListener(this)
        btn_skip_next.setOnClickListener(this)
        btn_repeat.setOnClickListener(this)
        btn_mine.setOnClickListener(this)
        btn_downloaded.setOnClickListener(this)

        displayWidth = windowManager.defaultDisplay.width
        progress_horizontal.max = displayWidth

        progress_horizontal.setOnTouchListener { _, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                progress_horizontal.progress = event.x.toInt()
                counterMillis = ( (event.x * ( timeline / 1000) ) / displayWidth ).toInt()
                progressLive()
            }
            true
        }

        progressLive()

        setUpData()
    }

    private fun progressLive() {
        if( this::CountDownTimer.isInitialized ) CountDownTimer.cancel()
        tEnd.text = timeUI(timeline/1000)
        CountDownTimer = object : CountDownTimer(( timeline - counterMillis*1000 ).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressUI()
            }

            override fun onFinish() {
                btn_skip_next.callOnClick()
            }

        }.start()

    }

    fun timeUI(i:Int):String = if(i>59) "${i/60}:${if(i%(60*(i/60))<10)"0${i%(60*(i/60))}" else  i%(60*(i/60)) }" else "0:${if(i<10)"0$i" else i}"

    /* --------  Progressbar UI components  ------------*/

    var doRepeat =  launch {  get_data(this@MediaPlayer, arrayListOf(Data.music_player,0,"do_repeat",false)) }
    var doMix = launch { get_data(this@MediaPlayer, arrayListOf(Data.music_player,0,"do_mix",false)) }

    fun progressUI(){
        counterMillis++
        progress_horizontal.progress = (counterMillis*100/(timeline/1000))
        progress_horizontal.secondaryProgress = (counterMillis*100/(timeline/1000))+15
        tStart.text = timeUI(counterMillis)
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
                isPlaying = if(isPlaying){
                    btn_start.setImageResource(R.drawable.ic_play)
                    CountDownTimer.cancel()
                    false
                }else{
                    btn_start.setImageResource(R.drawable.ic_pause)
                    progressLive()
                    true
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

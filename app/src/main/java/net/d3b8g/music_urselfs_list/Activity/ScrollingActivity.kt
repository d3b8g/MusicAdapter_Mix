package net.d3b8g.music_urselfs_list.Activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.coroutines.*
import me.anwarshahriar.calligrapher.Calligrapher
import net.d3b8g.music_urselfs_list.Adapter.MusicListAdapter
import net.d3b8g.music_urselfs_list.Interface.MusicView
import net.d3b8g.music_urselfs_list.Model.MusicParcel
import net.d3b8g.music_urselfs_list.R
import net.d3b8g.music_urselfs_list.SERVER_FAKE.MusicParamString
import net.d3b8g.music_urselfs_list.Shared.Data
import net.d3b8g.music_urselfs_list.Shared.Data.Companion.is_saved
import net.d3b8g.music_urselfs_list.Shared.get_data
import net.d3b8g.music_urselfs_list.Shared.set_data
import kotlin.coroutines.CoroutineContext

class ScrollingActivity : AppCompatActivity(),CoroutineScope,MusicView {

    lateinit var musicListAdapter:MusicListAdapter
    lateinit var list_view:RecyclerView
    lateinit var btn_add_file:Button
    lateinit var frame:FrameLayout

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        //fonts
        var calligraphy = Calligrapher(this)
        calligraphy.setFont(this,"Roboto.ttf",true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        musicListAdapter = MusicListAdapter()

        list_view = findViewById(R.id.music_list)
        frame = findViewById(R.id.mini_player)

        launch {
            if(get_data(this@ScrollingActivity, arrayListOf(Data.shared_name,0, is_saved,false) ) as Boolean){
                list_view.visibility = View.VISIBLE
                list_view.adapter = musicListAdapter
                list_view.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
                list_view.setHasFixedSize(true)
                musicListAdapter.update(MusicParamString().musicListRecording(MusicParamString().getMusicFromServer()))
            }else{
                Toast.makeText(this@ScrollingActivity,"U shwl add ur music",Toast.LENGTH_LONG).show()
            }
            job.join()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings ->{
                launch {
                    var param:ArrayList<Any> = ArrayList()
                    param.add(Data.shared_name)
                    param.add(0)
                    param.add(is_saved)
                    param.add(false)
                    set_data(this@ScrollingActivity,param)
                }
            }
        }

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun loadMusicComponents(musicList: ArrayList<MusicParcel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Interface


    override fun startLoadMusic() {

    }

    override fun errorMessage() {

    }

    override fun endLoadMusic() {

    }


}

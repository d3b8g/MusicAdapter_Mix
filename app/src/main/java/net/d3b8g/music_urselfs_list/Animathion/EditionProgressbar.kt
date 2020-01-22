package net.d3b8g.music_urselfs_list.Animathion

import android.content.Context
import android.graphics.Canvas
import android.widget.ProgressBar
import kotlinx.coroutines.runBlocking
import java.util.logging.Handler

class EditionProgressbar(context: Context?) : ProgressBar(context) {
    private var mLevel = 0
    private var fromLevel = 0
    private var toLevel = 0

    companion object{
        const val MAX_LEVEL = 10000
        const val LEVEL_DIFF = 100
        const val DELAY = 30
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

}
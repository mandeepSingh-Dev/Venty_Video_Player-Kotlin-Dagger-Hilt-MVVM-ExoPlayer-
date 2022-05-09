package com.Mandeep.ventyvideoplayer

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.navArgs
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.databinding.ActivityPlayerBinding
import com.Mandeep.ventyvideoplayer.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class PlayerActivity : AppCompatActivity()
{
    lateinit  var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    private var arrayList:ArrayList<MediaClasss>?=null
    var mediaItemList:ArrayList<MediaItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root  )




        val bundle = intent.getBundleExtra("bundle")
        if(bundle!=null)
        {
            bundle.let {
              //  arrayList =  it.getParcelableArrayList("VIDEOLIST")
                arrayList = it.getSerializable("VIDEOLIST") as ArrayList<MediaClasss>
                Log.d("8tfy3fg",arrayList?.size.toString())
                currentItem = it.getInt("POSITION")
                Log.d("w37rfg3f",currentItem.toString())
                //  link = it.getString("LINK")
            }
        }

        /* binding.videoView.setOnClickListener {
             position++
             releasePlayer()
             initializePlayer(position)
         }*/
        binding.videoVieww.setShowNextButton(true)
        arrayList?.forEach {

            val mediaItem = MediaItem.fromUri(it.link!!)
            mediaItemList.add(mediaItem)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        val params = PictureInPictureParams.Builder().build()

            enterPictureInPictureMode(params)
    }

    private fun initializePlayer(pos:Int) {
        player = ExoPlayer.Builder(this).build()
            .also {
                binding.videoVieww.player = it

                /*val mediaItem = MediaItem.fromUri(arrayList?.get(pos)?.link!!)
                it.setMediaItem(mediaItem)*/
                it.setMediaItems(mediaItemList)
                it.playWhenReady = playWhenReady
                it.seekTo(currentItem,playbackPosition)
                it.prepare()

            } }
        //  player?.setMediaItems(mediaItemList)

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer(currentItem)
        }
    }
    override fun onResume() {
        super.onResume()
      //  hideSystemUi()
        //  requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if ((Build.VERSION.SDK_INT <= 23 || player == null)) {
            initializePlayer(currentItem)
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window!!, false)
        WindowInsetsControllerCompat(window!!, binding.videoVieww).let { controller ->
            controller.hide(WindowInsetsCompat.Type.displayCutout())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    public override fun onPause() {
        super.onPause()
        // requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
    }
    public override fun onStop() {
        super.onStop()
        // requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentItem = it.currentMediaItemIndex
            playWhenReady = it.playWhenReady
            it.release()
        }
        player = null
    }
}
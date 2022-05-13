package com.Mandeep.ventyvideoplayer

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class PlayerActivity : AppCompatActivity()
{
    lateinit  var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    private var arrayList:ArrayList<MediaClasss>?=null
    var mediaItemList:ArrayList<MediaItem> = ArrayList()

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        Log.d("LifecycleMethods", "onCreate");

        currentItem = intent.getIntExtra("POSITION",0)
        Log.d("w37rfg3f", currentItem.toString())

        intent.getBundleExtra("bundle")?.let {
            //  arrayList =  it.getParcelableArrayList("VIDEOLIST")
            arrayList = it.getSerializable("VIDEOLIST") as ArrayList<MediaClasss>
            Log.d("8tfy3fg", arrayList?.size.toString())
            //  link = it.getString("LINK")
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

        initializePlayer()
    }



    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build()
            .also {
                binding.videoVieww.player = it
                it.setMediaItems(mediaItemList)
                /*val mediaItem = MediaItem.fromUri(arrayList?.get(pos)?.link!!)
                it.setMediaItem(mediaItem)*/
                 it.playWhenReady = playWhenReady
                 it.seekTo(currentItem, playbackPosition)

                it.prepare()
            } }
        //  player?.setMediaItems(mediaItemList)

    override fun onStart() {
        super.onStart()
        Log.d("LifecycleMethods", "onstart");
      //  player?.playWhenReady = true
      //  player?.playbackState
        if (Build.VERSION.SDK_INT > 23) {
            Log.d("388ry3t","onstart INILIZED")
           // initializePlayer(currentItem)
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("LifecycleMethods", "onResume");

       // player?.playWhenReady = true
      //  player?.playbackState
        ispipMode?.let {
            if (it) {
              //  playWhenReady = true
               // currentItem = player?.currentMediaItemIndex
              ///  playbackPosition = player?.currentPosition
            }
        }

        //  hideSystemUi()
        //  requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if (Build.VERSION.SDK_INT <= 23 /*|| player == null)*/) {
            Log.d("388ry3t","ONRESUME INILIZED")
            initializePlayer()
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

  /*  @RequiresApi(Build.VERSION_CODES.N)
    public override fun onPause() {
        super.onPause()

        playWhenReady = false
        currentItem = player?.currentMediaItemIndex
        playbackPosition = player?.currentPosition!!
        //player?.playWhenReady = false
        //player?.playbackState
        if(isInPictureInPictureMode) {
            Log.d("3f83hf","pipMODE")
            //player?.playWhenReady = true
            playWhenReady = true
            currentItem = player?.currentMediaItemIndex
            playbackPosition = player?.currentPosition!!
        }
        else{
            Log.d("3f83hf","NO_pipMODE")

         //   player?.playWhenReady = false
          //  player?.playbackState
            playWhenReady = false
            currentItem = player?.currentMediaItemIndex
            playbackPosition = player?.currentPosition!!

        }
        Log.d("LifecycleMethods", "onPause");

        // requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
    }*/


    public override fun onStop() {
        super.onStop()
        Log.d("LifecycleMethods", "onStop");

        releasePlayer()
       /* ispipMode?.let {
            if(it)
            {
                playWhenReady = true
                currentItem = player?.currentMediaItemIndex!!
                playbackPosition = player?.currentPosition!!
               player?.release()
               // finish()

            }
        }*/

        // requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        if (Build.VERSION.SDK_INT > 23) {
           // releasePlayer()


           //  player?.currentPosition?.let { playbackPosition = it }
           //  player?.currentMediaItemIndex?.let {currentItem = it }
           // player?.playWhenReady?.let {  playWhenReady =it }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LifecycleMethods","onRestart")
       playWhenReady = true
        currentItem = player?.currentMediaItemIndex!!
        playbackPosition = player?.currentPosition!!
        //player?.playWhenReady = true
        player?.playbackState
    }


    private fun releasePlayer() {
        Log.d("RELEASED","Released")
        player?.let {
            playbackPosition = it.currentPosition
            Log.d("39ry373",playbackPosition.toString())
            currentItem = it.currentMediaItemIndex
            playWhenReady = it.playWhenReady
            it.release()
        }
        player = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleMethods", "onDestroy");

    }





    var ispipMode:Boolean?=null
    var mBackstackLost = false

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        ispipMode = isInPictureInPictureMode

        if(isInPictureInPictureMode)
        {
            Log.d("rg489tgh4g","true")
            binding.videoVieww.hideController()
        }else{
            mBackstackLost = true
            Log.d("rg489tgh4g","false")
            binding.videoVieww.showController()
        }
    }


    override fun finish() {
        if (mBackstackLost) {
            Log.d("rg489tgh4g","TRUEBACKSTACK")

            finishAndRemoveTask()
            startActivity(Intent.makeRestartActivityTask(ComponentName(this, MainActivity::class.java)).setFlags(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS))

        } else {
            Log.d("rg489tgh4g","falseBACKSTACK")

            super.finish()
        }
    }

   /* override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val  pictureInpicture =   PictureInPictureParams.Builder()
            enterPictureInPictureMode(pictureInpicture.build())

            playWhenReady = false
            currentItem = player?.currentMediaItemIndex
            playbackPosition = player?.currentPosition!!

            Log.d("39ry373",playbackPosition.toString())
          //  currentItem = player?.currentMediaItemIndex
          //  playWhenReady = player?.playWhenReady
            player?.release()
           // finish()
        }

    }*/

    override fun onUserLeaveHint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isInPictureInPictureMode) {
                val pictureInpicture = PictureInPictureParams.Builder()
                enterPictureInPictureMode(pictureInpicture.build())
            }
        }


    }


}
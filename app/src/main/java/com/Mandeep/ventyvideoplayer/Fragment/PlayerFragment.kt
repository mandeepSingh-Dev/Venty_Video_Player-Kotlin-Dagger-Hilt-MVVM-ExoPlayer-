package com.Mandeep.ventyvideoplayer.Fragment

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.MVVM.MyAdapter
import com.Mandeep.ventyvideoplayer.R
import com.Mandeep.ventyvideoplayer.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import java.net.URL
import javax.inject.Inject


class PlayerFragment @Inject constructor() : Fragment() {

     lateinit  var binding:FragmentPlayerBinding
    private var player: ExoPlayer? = null
    private var arrayList:ArrayList<MediaClasss>?=null
    var link:String?=null
    var mediaItemList:ArrayList<MediaItem> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(arguments!=null)
        {
            arguments?.let {
               arrayList =  it.getParcelableArrayList("VIDEOLIST")
                currentItem = it.getInt("POSITION")
              //  link = it.getString("LINK")
            }
        }

       /* binding.videoView.setOnClickListener {
            position++
            releasePlayer()
            initializePlayer(position)
        }*/
        binding.videoView.setShowNextButton(true)
        arrayList?.forEach {

            val mediaItem = MediaItem.fromUri(it.link!!)
            mediaItemList.add(mediaItem)
        }

    }


    private fun initializePlayer(pos:Int) {
        player = context?.let { ExoPlayer.Builder(it).build()
                .also {
                    binding.videoView.player = it

                    /*val mediaItem = MediaItem.fromUri(arrayList?.get(pos)?.link!!)
                    it.setMediaItem(mediaItem)*/
                    it.setMediaItems(mediaItemList)
                    it.playWhenReady = playWhenReady
                    it.seekTo(currentItem,playbackPosition)
                    it.prepare()

                } }
               //  player?.setMediaItems(mediaItemList)



    }

     override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer(currentItem)
        }
    }
     override fun onResume() {
        super.onResume()
        hideSystemUi()
       //  requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if ((Build.VERSION.SDK_INT <= 23 || player == null)) {
            initializePlayer(currentItem)
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(activity?.window!!, false)
        WindowInsetsControllerCompat(activity?.window!!, binding.videoView).let { controller ->
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

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(),"onDestory", Toast.LENGTH_SHORT).show()
    }



}
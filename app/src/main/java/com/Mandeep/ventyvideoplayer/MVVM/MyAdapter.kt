package com.Mandeep.ventyvideoplayer.MVVM

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.Mandeep.ventyvideoplayer.PlayerActivity
import com.Mandeep.ventyvideoplayer.R
import com.Mandeep.ventyvideoplayer.Util.FetchVideos
import com.Mandeep.ventyvideoplayer.Util.ItemLayouts
import com.google.android.exoplayer2.Player

class MyAdapter(val context: Context, var videoList: ArrayList<MediaClasss>?, private val mode: ItemLayouts): RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{

    lateinit var fetchVideos: FetchVideos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = when(mode) {
        ItemLayouts.VIDEO_ITEM_LAYOUT -> LayoutInflater.from(context).inflate(R.layout.video_items, parent, false)
        else -> LayoutInflater.from(context).inflate(R.layout.bucket_item, parent, false)
    }
         fetchVideos = FetchVideos()
        return MyViewHolder(view,mode)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (mode)
        {
            ItemLayouts.VIDEO_ITEM_LAYOUT -> {
                val mediaClasss =  videoList?.get(position)
                holder.artistTextView?.setText(mediaClasss?.artistName)
                holder.songName?.setText(mediaClasss?.songName)

              //  Log.d("38ry3f3",videoList?.get(position)?.bitmap.toString())
               try {
                   val baos = videoList?.get(position)?.baos
                   val bitmap = BitmapFactory.decodeByteArray(baos, 0, baos?.size!!)
                   holder.thumnailImageView?.setImageBitmap(bitmap)

               }catch (e:Exception){
                       holder.thumnailImageView?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_launcher_background,null))

               }

                Log.d("kfndkjfd",videoList?.size.toString())
                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                   // bundle.putString("ARTIST",mediaClasss?.artistName)
                  //  bundle.putString("SONG",mediaClasss?.songName)
                    //bundle.putString("LINK",mediaClasss?.link)
                  //  bundle.putSerializable("VLIST",videoList)
                    bundle.putSerializable("VIDEOLIST",videoList)
                    bundle.putInt("POSITION",position)

                   //Navigation.findNavController(it).navigate(R.id.playerFragment,bundle)

                    val intent1 = Intent(context,PlayerActivity::class.java)
                    intent1.putExtra("bundle",bundle)
                    context.startActivity(intent1)
                }
            }
            else -> {
                if(videoList!=null ) {
                    Log.d("difhdtf673egf3", videoList?.size.toString())
                    holder.buckettextView?.text = videoList?.get(position)?.bucket
                    holder.relativepathTextView?.text = videoList?.get(position)?.relativePath

                    holder.itemView.setOnClickListener{
                   val buckVideoList = fetchVideos.fetchBucketVideos(context,videoList?.get(position)?.bucket!!)
                   val bundle = Bundle()
                        bundle.putString("BUCKET_NAME",videoList?.get(position)?.bucket)
                        Navigation.findNavController(it).navigate(R.id.bucketVideosFragment,bundle)
                    }
                }
            }
        }



    }

    override fun getItemCount(): Int {
        return  videoList?.size!!


    }

    class MyViewHolder(itemView: View,mode:ItemLayouts):RecyclerView.ViewHolder(itemView)
    {
         var artistTextView: TextView?=null
         var songName:TextView?=null
        var thumnailImageView:ImageView?=null

        var buckettextView:TextView?=null
        var relativepathTextView:TextView?=null
        init {
            when(mode)
            {
                ItemLayouts.VIDEO_ITEM_LAYOUT-> {
                    artistTextView =   itemView.findViewById(R.id.artistNameTextView)
                    songName   = itemView.findViewById(R.id.songNameTextview)
                    thumnailImageView = itemView.findViewById(R.id.thumnail1)
                }
                else -> {
                    buckettextView =   itemView.findViewById(R.id.bucketTextView)
                    relativepathTextView =  itemView.findViewById(R.id.relativePathTextView)
                }
            }

        }
    }
}
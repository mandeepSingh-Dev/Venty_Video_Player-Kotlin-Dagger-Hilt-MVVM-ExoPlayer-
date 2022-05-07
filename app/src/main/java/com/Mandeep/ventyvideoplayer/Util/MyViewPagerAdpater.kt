package com.Mandeep.ventyvideoplayer.Util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.Mandeep.ventyvideoplayer.Fragment.AllVideosFragment
import com.Mandeep.ventyvideoplayer.Fragment.VideoFoldersFragment
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss

class MyViewPagerAdpater(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
     return if(position==0)
      {
          val frag =  AllVideosFragment()
          val arraylist = ArrayList<MediaClasss>()
          arraylist.add(MediaClasss("sdsd","dsds","sdsds","sdsds","Sdsds"))
         // frag.onSendArrayListListener(arraylist)
          frag
      }else{
           VideoFoldersFragment()
      }
    }

}
package com.tugasakhir.welearn.presentation.ui.score

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ScoreAngkaFragment()
            1 -> fragment = ScoreHurufFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount() = 2
}
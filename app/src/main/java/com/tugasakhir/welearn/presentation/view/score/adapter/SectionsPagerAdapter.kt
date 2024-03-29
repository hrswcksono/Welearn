package com.tugasakhir.welearn.presentation.view.score.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreAngkaFragment
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreHurufFragment

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
package com.tugasakhir.welearn.presentation.view.score

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tugasakhir.welearn.presentation.view.RuleMultiPlayerFragment
import com.tugasakhir.welearn.presentation.view.RuleSinglePlayerFragment
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreAngkaFragment
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreHurufFragment

class RulePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RuleSinglePlayerFragment()
            1 -> fragment = RuleMultiPlayerFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount() = 2
}
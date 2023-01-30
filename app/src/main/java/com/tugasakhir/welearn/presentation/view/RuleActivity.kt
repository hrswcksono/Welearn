package com.tugasakhir.welearn.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityRuleBinding
import com.tugasakhir.welearn.presentation.view.score.RulePagerAdapter
import com.tugasakhir.welearn.presentation.view.score.adapter.SectionsPagerAdapter
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreActivity

class RuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRuleBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.single,
            R.string.multi
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.papanScoreBack.setOnClickListener {
            onBackPressed()
        }

        val sectionsPagerAdapter = RulePagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager_rule)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(RuleActivity.TAB_TITLES[position])
        }.attach()
    }
}
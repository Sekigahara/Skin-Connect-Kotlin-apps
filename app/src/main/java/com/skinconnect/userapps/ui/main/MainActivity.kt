package com.skinconnect.userapps.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.ActivityMainBinding
import com.skinconnect.userapps.ui.helper.BaseActivity
import com.skinconnect.userapps.ui.main.profile.ProfileFragment
import com.skinconnect.userapps.ui.main.schedule.ScheduleFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
        setupView()
    }

    override fun setupView() {
        val homeFragment = HomeFragment()
        val scheduleFragment = ScheduleFragment()
        val profileFragment = ProfileFragment()
        setCurrentFragment(homeFragment)
        val bottomNavBar = (binding as ActivityMainBinding).bottomNavigationView as NavigationBarView
        bottomNavBar.selectedItemId = R.id.homeFragment

        bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.scheduleFragment -> {
                    setCurrentFragment(scheduleFragment)
                    true
                }
                R.id.profileFragment -> {
                    setCurrentFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.commit {
        replace(R.id.frameLayoutMain, fragment)
    }
}
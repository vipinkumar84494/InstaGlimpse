package com.vk.instaglimpse

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.vk.instaglimpse.databinding.ActivityMainBinding
import com.vk.instaglimpse.fragments.HomeFragment
import com.vk.instaglimpse.fragments.NotificationsFragment
import com.vk.instaglimpse.fragments.ProfileFragment
import com.vk.instaglimpse.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {

                    navigateFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_search -> {
                    navigateFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_add_post -> {

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_notifications -> {

                    navigateFragment(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {

                    navigateFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        navigateFragment(HomeFragment())

    }

    private fun navigateFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().
        replace(R.id.nav_host_fragment,fragment)
            .commit()
    }

}
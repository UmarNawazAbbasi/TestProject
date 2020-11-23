package com.umarapps.citrudbitsinterviewtest.ui.Activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umarapps.citrudbitsinterviewtest.R
import com.umarapps.citrudbitsinterviewtest.ui.Fragments.AlbumsListFragment
import com.umarapps.citrudbitsinterviewtest.ui.Fragments.PhotosListFragment
import com.umarapps.citrudbitsinterviewtest.ui.Fragments.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUiWidgtes(savedInstanceState)
    }
    private fun setUiWidgtes(savedInstanceState: Bundle?)
    {
        if (savedInstanceState == null) {
            val fragment = UserListFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_users -> {
                val fragment = UserListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_albums -> {
                val fragment = AlbumsListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_photos -> {
                val fragment = PhotosListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
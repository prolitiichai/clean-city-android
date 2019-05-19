package ru.prolitiichai.cleancity.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Window
import ru.prolitiichai.cleancity.R

class MainActivity : AppCompatActivity() {

    companion object {
        var lastFragmetTag: String? = null
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_map -> {
                MapActivity.newInstance()
            }
            R.id.navigation_profile -> {
                ProfileActivity.newInstance()
            }
            else -> {
                null
            }
        }

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()

            if (lastFragmetTag != null) {
                val lastFragment = supportFragmentManager.findFragmentByTag(lastFragmetTag)
                if (lastFragment != null)
                    transaction.hide(lastFragment)
            }

            if (!fragment.isAdded) {
                transaction.add(R.id.fragmentContainer, fragment, tag)
            } else {
                transaction.show(fragment)
            }

            transaction.commit()

            lastFragmetTag = tag
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        loadFragment(item.itemId)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        loadFragment(R.id.navigation_profile)
    }
}

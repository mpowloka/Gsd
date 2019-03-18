package com.mpowloka.gsd

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.userdetails.UserDetailsFragment
import com.mpowloka.gsd.userlist.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (fragment_container?.childCount == 0) {
            openFragment(UserListFragment.newInstance())
        }
    }

    override fun <T : Fragment> openFragment(fragment: T) {

        if (!resources.getBoolean(R.bool.isLandscapeTablet)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun <T : Fragment> setupActionBar(fragment: T, title: String?) {

        toolbar.menu.clear()

        when {
            resources.getBoolean(R.bool.isLandscapeTablet) -> {
                supportActionBar?.title = getString(R.string.app_name)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                menuInflater.inflate(R.menu.search_user_menu, toolbar.menu)
            }

            fragment is UserListFragment -> {
                supportActionBar?.title = getString(R.string.app_name)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                menuInflater.inflate(R.menu.search_user_menu, toolbar.menu)
            }

            fragment is UserDetailsFragment -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = title
            }
        }
    }

}

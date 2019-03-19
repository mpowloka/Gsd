package com.mpowloka.gsd

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mpowloka.gsd.common.GsdApplication
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.common.ViewModelFactory
import com.mpowloka.gsd.userdetails.UserDetailsFragment
import com.mpowloka.gsd.userlist.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationComponent {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)

        setSupportActionBar(toolbar)

        if (fragment_container?.childCount == 0) {
            openFragment(UserListFragment.newInstance(), false)
        }
    }

    override fun <T : Fragment> openFragment(fragment: T, addToBackStack: Boolean) {

        if (!resources.getBoolean(R.bool.isLandscapeTablet)) {
            val transaction = supportFragmentManager.beginTransaction()

            if (addToBackStack) {
                transaction.addToBackStack(null)
            }

            transaction
                .replace(R.id.fragment_container, fragment)
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

            resources.getBoolean(R.bool.isLandscapeTablet) || fragment is UserListFragment -> {
                supportActionBar?.title = getString(R.string.app_name)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                menuInflater.inflate(R.menu.search_user_menu, toolbar.menu)

                (toolbar.menu.findItem(R.id.action_search)?.actionView as? SearchView)?.apply {
                    queryHint = getString(R.string.search_hint)
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String): Boolean {
                            viewModel.searchPressed(query)
                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            viewModel.setCurrentPhrase(newText)
                            return true
                        }

                    })
                }
            }

            fragment is UserDetailsFragment -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = title
            }
        }
    }

}

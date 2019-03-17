package com.mpowloka.gsd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.userlist.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()

        if (savedInstanceState == null) {
            setupInitialFragment()
        }
    }

    override fun <T : Fragment> openFragment(fragment: T) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupInitialFragment() = openFragment(UserListFragment.newInstance())

}

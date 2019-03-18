package com.mpowloka.gsd.common

import androidx.fragment.app.Fragment

interface NavigationComponent {

    fun <T : Fragment> openFragment(fragment: T)

    fun <T : Fragment> setupActionBar(fragment: T, title: String? = null)

}
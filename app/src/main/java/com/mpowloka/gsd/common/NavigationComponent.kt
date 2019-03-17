package com.mpowloka.gsd.common

import androidx.fragment.app.Fragment

interface NavigationComponent {

    fun <T : Fragment> openFragment(fragment: T)

}
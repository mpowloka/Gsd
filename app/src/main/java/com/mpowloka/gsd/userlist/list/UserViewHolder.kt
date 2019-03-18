package com.mpowloka.gsd.userlist.list

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.userdetails.UserDetailsFragment
import com.mpowloka.gsd.userlist.UserListViewModel
import kotlinx.android.synthetic.main.view_holder_user.view.*

class UserViewHolder(
    itemView: View,
    private val navigationComponent: NavigationComponent,
    private val viewModel: UserListViewModel
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User, selected: Boolean): Unit = with(itemView) {
        user_login_tv.text = user.login
        Glide.with(context).load(user.avatarUrl).into(user_picture_iv)

        if (resources.getBoolean(R.bool.isLandscapeTablet)) {
            setupSelection(selected)
        }

        card.setOnClickListener {
            viewModel.setCurrentUser(user)
            navigationComponent.openFragment(UserDetailsFragment.newInstance())
        }
    }

    private fun View.setupSelection(selected: Boolean) {
        card.setCardBackgroundColor(
            ResourcesCompat.getColor(
                rootView.resources,
                if (selected) R.color.colorAccent else R.color.white,
                null
            )
        )
    }

}
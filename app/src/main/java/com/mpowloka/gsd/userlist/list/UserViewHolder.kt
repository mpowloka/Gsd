package com.mpowloka.gsd.userlist.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

    fun bind(user: User): Unit = with(itemView) {
        user_login_tv.text = user.login
        Glide.with(context).load(user.avatarUrl).into(user_picture_iv)

        card.setOnClickListener {
            viewModel.setCurrentUser(user)
            navigationComponent.openFragment(UserDetailsFragment.newInstance())
        }
    }

}
package com.mpowloka.gsd.userlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.domain.user.User

class UserListRecyclerAdapter(
    private val navigationComponent: NavigationComponent
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        NO_INTERNET_WARNING_TYPE -> NoInternetWarningViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_no_internet_warning, parent, false)
        )

        USER_TYPE -> UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_user, parent, false),
            navigationComponent
        )

        else -> throw IllegalStateException(
            "Unsupported viewType: $viewType"
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is UserViewHolder && item is Item.UserItem) {
            holder.bind(item.login, item.pictureUrl)
        }
    }

    override fun getItemViewType(position: Int) = when (items[position]) {

        is Item.NoInternetWarningItem -> NO_INTERNET_WARNING_TYPE

        is Item.UserItem -> USER_TYPE

    }

    override fun getItemCount() = items.size

    sealed class Item {

        object NoInternetWarningItem : Item()

        data class UserItem(val login: String, val pictureUrl: String) : Item() {
            constructor(user: User) : this(user.login, user.avatarUrl)
        }

    }

    companion object {

        private const val NO_INTERNET_WARNING_TYPE = 0

        private const val USER_TYPE = 1
    }
}
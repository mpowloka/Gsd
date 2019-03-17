package com.mpowloka.gsd.userlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpowloka.gsd.R

class UserListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                .inflate(R.layout.view_holder_user, parent, false)
        )

        else -> throw IllegalStateException(
            "Unsupported viewType: $viewType"
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is UserViewHolder && item is Item.User) {
            holder.bind(item.login, item.pictureUrl)
        }
    }

    override fun getItemViewType(position: Int) = when (items[position]) {

        is Item.NoInternetWarning -> NO_INTERNET_WARNING_TYPE

        is Item.User -> USER_TYPE

    }

    override fun getItemCount() = items.size

    sealed class Item {

        object NoInternetWarning: Item()

        class User(val login: String, val pictureUrl: String): Item()

    }

    companion object {

        private const val NO_INTERNET_WARNING_TYPE = 0

        private const val USER_TYPE = 1
    }
}
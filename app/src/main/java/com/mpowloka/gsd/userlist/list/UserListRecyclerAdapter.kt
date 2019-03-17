package com.mpowloka.gsd.userlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.userlist.UserListViewModel

class UserListRecyclerAdapter(
    private val navigationComponent: NavigationComponent,
    private val viewModel: UserListViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setHasStableIds(true)
    }

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
            navigationComponent,
            viewModel
        )

        else -> throw IllegalStateException(
            "Unsupported viewType: $viewType"
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is UserViewHolder && item is Item.UserItem) {
            holder.bind(item.user)
        }
    }

    override fun getItemViewType(position: Int) = when (items[position]) {

        is Item.NoInternetWarningItem -> NO_INTERNET_WARNING_TYPE

        is Item.UserItem -> USER_TYPE

    }

    override fun getItemId(position: Int): Long {
        val item = items[position]
        return when(item) {

            is Item.UserItem -> item.user.userId

            Item.NoInternetWarningItem -> -1L
        }
    }

    override fun getItemCount() = items.size

    sealed class Item {

        object NoInternetWarningItem : Item()

        data class UserItem(val user: User) : Item()

    }

    companion object {

        private const val NO_INTERNET_WARNING_TYPE = 0

        private const val USER_TYPE = 1
    }
}
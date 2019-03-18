package com.mpowloka.gsd.userlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.userlist.UserListViewModel

class UserListRecyclerAdapter(
    private val navigationComponent: NavigationComponent,
    private val viewModel: UserListViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    var data: UserListAdapterData = UserListAdapterData(
        emptyList(),
        null
    )
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
        val item = data.items[position]

        if (holder is UserViewHolder && item is UserListAdapterData.Item.UserItem) {
            holder.bind(item.user, item == data.selectedItem)
        }
    }

    override fun getItemViewType(position: Int) = when (data.items[position]) {

        is UserListAdapterData.Item.NoInternetWarningItem -> NO_INTERNET_WARNING_TYPE

        is UserListAdapterData.Item.UserItem -> USER_TYPE

    }

    override fun getItemId(position: Int): Long {
        val item = data.items[position]
        return when (item) {

            is UserListAdapterData.Item.UserItem -> item.user.userId

            UserListAdapterData.Item.NoInternetWarningItem -> -1L
        }
    }

    override fun getItemCount() = data.items.size

    companion object {

        private const val NO_INTERNET_WARNING_TYPE = 0

        private const val USER_TYPE = 1
    }
}
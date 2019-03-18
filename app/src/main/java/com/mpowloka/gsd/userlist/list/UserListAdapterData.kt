package com.mpowloka.gsd.userlist.list

import com.mpowloka.gsd.domain.user.User

data class UserListAdapterData(
    val items: List<Item>,
    val selectedItem: Item?
) {

    sealed class Item {

        object NoInternetWarningItem : Item()

        data class UserItem(val user: User) : Item()

    }

}
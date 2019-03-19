package com.mpowloka.gsd.userlist.list

import com.mpowloka.gsd.domain.user.User

data class UserListAdapterData(
    val items: List<Item>,
    val selectedItem: Item?
) {

    sealed class Item {

        class WarningItem(val warning: String) : Item()

        class MessageItem(val message: String) : Item()

        data class UserItem(val user: User) : Item()

    }

}
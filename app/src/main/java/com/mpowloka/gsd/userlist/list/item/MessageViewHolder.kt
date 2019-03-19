package com.mpowloka.gsd.userlist.list.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_message.view.*

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: String): Unit = with(itemView) {
        message_tv.text = message
    }

}
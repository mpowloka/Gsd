package com.mpowloka.gsd.userlist.list.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_warning.view.*

class WarningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(warning: String): Unit = with(itemView) {
        warning_tv.text = warning
    }

}
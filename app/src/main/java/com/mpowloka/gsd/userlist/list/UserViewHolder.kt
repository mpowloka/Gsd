package com.mpowloka.gsd.userlist.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_holder_user.view.*

class UserViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(login: String, pictureUrl: String): Unit = with(itemView) {
        user_login_tv.text = login
        Glide.with(context).load(pictureUrl).into(user_picture_iv)

        card.setOnClickListener {
            //TODO
        }
    }

}
package com.mpowloka.gsd.userlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.userlist.list.UserListRecyclerAdapter

import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context ?: return
        val navigationComponent = (activity as? NavigationComponent) ?: return

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = UserListRecyclerAdapter(navigationComponent).apply {
            items = listOf(
                UserListRecyclerAdapter.Item.NoInternetWarningItem,
                UserListRecyclerAdapter.Item.UserItem(
                    "szumi",
                    "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
                ),
                UserListRecyclerAdapter.Item.UserItem("scheja", "https://avatars3.githubusercontent.com/u/583231?v=4"),
                UserListRecyclerAdapter.Item.UserItem("sancia", "https://avatars3.githubusercontent.com/u/583231?v=4"),
                UserListRecyclerAdapter.Item.UserItem("seycher", "https://avatars3.githubusercontent.com/u/583231?v=4"),
                UserListRecyclerAdapter.Item.UserItem("tomokene", "https://avatars3.githubusercontent.com/u/583231?v=4")

            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_user_menu, menu)

        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    //TODO
                    return true
                }

            })
        }
    }

    companion object {

        fun newInstance() = UserListFragment()

    }

}
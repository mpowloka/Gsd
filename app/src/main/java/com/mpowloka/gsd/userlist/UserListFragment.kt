package com.mpowloka.gsd.userlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.common.ViewModelFactory
import com.mpowloka.gsd.userlist.list.UserListRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment() {

    lateinit var viewModel: UserListViewModel

    private lateinit var usersToDisplayDisposable: Disposable
    private lateinit var recyclerAdapter: UserListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance()
        ).get(UserListViewModel::class.java)

        val context = context ?: return
        val navigationComponent = (activity as? NavigationComponent) ?: return
        recyclerAdapter = UserListRecyclerAdapter(navigationComponent, viewModel)

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = recyclerAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navigationComponent = activity as? NavigationComponent
        navigationComponent?.setupActionBar(this)

    }

    override fun onResume() {
        super.onResume()

        usersToDisplayDisposable = viewModel.itemsToDisplay
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recyclerAdapter.items = it
            }
    }

    override fun onPause() {
        super.onPause()

        usersToDisplayDisposable.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (menu.findItem(R.id.action_search)?.actionView as? SearchView)?.apply {
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String) = false

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.nextSearchPhrase(newText)
                    return true
                }

            })
        }
    }

    companion object {

        fun newInstance() = UserListFragment()

    }

}
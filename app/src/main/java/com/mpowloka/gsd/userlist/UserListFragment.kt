package com.mpowloka.gsd.userlist

import android.os.Bundle
import android.view.*
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
        val application = activity?.application ?: return

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance(application)
        ).get(UserListViewModel::class.java)

        viewModel.initializeFirstCurrentUserIfNeeded()

        setupRecyclerView()

    }

    override fun onResume() {
        super.onResume()

        usersToDisplayDisposable = viewModel.adapterData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recyclerAdapter.data = it
            }
    }

    override fun onPause() {
        super.onPause()

        usersToDisplayDisposable.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        setupActionBar()

        observeSearchViewInput(menu)
    }

    private fun observeSearchViewInput(menu: Menu) {
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

    private fun setupRecyclerView() {
        val context = context ?: return
        val navigationComponent = (activity as? NavigationComponent) ?: return
        recyclerAdapter = UserListRecyclerAdapter(navigationComponent, viewModel)

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = recyclerAdapter
    }

    private fun setupActionBar() {
        val navigationComponent = activity as? NavigationComponent
        navigationComponent?.setupActionBar(this)
    }

    companion object {

        fun newInstance() = UserListFragment()

    }

}
package com.mpowloka.gsd.userlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpowloka.gsd.MainViewModel
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.GsdApplication
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.common.ViewModelFactory
import com.mpowloka.gsd.userlist.list.UserListAdapterData
import com.mpowloka.gsd.userlist.list.UserListRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment() {

    lateinit var viewModel: UserListViewModel
    lateinit var mainViewModel: MainViewModel

    private lateinit var recyclerAdapter: UserListRecyclerAdapter

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return
        getViewModels(activity)

        viewModel.initializeFirstCurrentUserIfNeeded()

        setupRecyclerView()

    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.addAll(

            viewModel.adapterData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    it.printStackTrace()
                    recyclerAdapter.data = UserListAdapterData(
                        listOf(UserListAdapterData.Item.WarningItem(it.stackTrace.toString())),
                        null
                    )
                }
                .subscribe {
                    recyclerAdapter.data = it
                },

            mainViewModel.currentPhrase
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe {
                    viewModel.nextSearchPhrase(it)
                },

            mainViewModel.searchClicks
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe {
                    viewModel.fetchUser(it)
                }

        )


    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        setupActionBar()
    }

    private fun getViewModels(activity: FragmentActivity) {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(activity.application as GsdApplication)
        ).get(UserListViewModel::class.java)

        mainViewModel = ViewModelProviders.of(
            activity,
            ViewModelFactory(activity.application as GsdApplication)
        ).get(MainViewModel::class.java)
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
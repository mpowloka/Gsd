package com.mpowloka.gsd.userlist

import android.app.Application
import androidx.lifecycle.ViewModel
import com.mpowloka.gsd.R
import com.mpowloka.gsd.domain.applicationstate.usecase.GetCurrentUserUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.GetInitialUserSetUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.SetCurrentUserUseCase
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.usecase.FetchUserUseCase
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase
import com.mpowloka.gsd.userlist.list.UserListAdapterData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class UserListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val setCurrentUserUseCase: SetCurrentUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getInitialUserSetUseCase: GetInitialUserSetUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val application: Application
) : ViewModel() {

    val adapterData: Observable<UserListAdapterData> by lazy {

        Observables.combineLatest(
            allUsers, getCurrentUserUseCase.get(), phraseSubject
        ) { users, currentUser, phrase ->

            val items = buildAdapterItems(users, phrase)

            UserListAdapterData(
                items,
                UserListAdapterData.Item.UserItem(currentUser)
            )
        }
    }

    private val phraseSubject = BehaviorSubject.create<String>().apply {
        onNext("")
    }

    private val allUsers: Observable<List<User>> by lazy {
        getAllUsersUseCase.get()
    }

    private val initialUserSet: Observable<Boolean> by lazy {
        getInitialUserSetUseCase.get()
    }

    private val compositeDisposable = CompositeDisposable()

    fun initializeFirstCurrentUserIfNeeded() {
        compositeDisposable.add(
            Observables
                .combineLatest(allUsers, initialUserSet)
                .first(Pair(emptyList(), false))
                .subscribeOn(Schedulers.io())
                .subscribe { pair ->
                    if(!pair.second) {
                        setCurrentUser(pair.first.getOrNull(0) ?: return@subscribe)
                    }
                }
        )
    }

    fun fetchUser(username: String) {
        fetchUserUseCase.fetch(username)
    }

    fun nextSearchPhrase(phrase: String) {
        phraseSubject.onNext(phrase)
    }

    fun setCurrentUser(user: User) {
        setCurrentUserUseCase.set(user)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun buildAdapterItems(
        users: List<User>,
        phrase: String
    ): MutableList<UserListAdapterData.Item> {

        val items = users.filter { it.login.contains(phrase) }.map {
            UserListAdapterData.Item.UserItem(it) as UserListAdapterData.Item
        }.toMutableList()

        if (items.isEmpty()) {
            items.add(UserListAdapterData.Item.MessageItem(
                application.getString(R.string.tap_search_message)
            ))
        }
        return items
    }

}
package com.mpowloka.gsd.userlist

import androidx.lifecycle.ViewModel
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase
import com.mpowloka.gsd.userlist.list.UserListRecyclerAdapter
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject

class UserListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val phraseSubject = BehaviorSubject.create<String>().apply {
        onNext("")
    }

    val usersToDisplay: Observable<List<UserListRecyclerAdapter.Item>> by lazy {
        Observables.combineLatest(
            getAllUsersUseCase.get(), phraseSubject
        ) { users, phrase ->
            users.filter { it.login.contains(phrase) }.map {
                UserListRecyclerAdapter.Item.UserItem(it) as UserListRecyclerAdapter.Item
            }
        }
    }

    fun nextSearchPhrase(phrase: String) {
        phraseSubject.onNext(phrase)
    }
}
package com.mpowloka.gsd.data.user

import android.annotation.SuppressLint
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class UsersRepositoryImpl(
    private val api: UsersApi
) : UsersRepository {

    private var allUsers = BehaviorSubject.createDefault(mutableSetOf<User>())

    @SuppressLint("CheckResult")
    override fun getAllUsers(): Observable<List<User>> {

        Observables.combineLatest(
            allUsers,
            api.getAllUsers()
        )
            .take(1)
            .subscribeOn(Schedulers.io())
            .subscribe { pair ->
                val updatedSet = pair.first.apply {
                    addAll(pair.second.map { it.toUser() }
                    )
                }
                allUsers.onNext(updatedSet)
            }

        return allUsers.map { it.toList() }
    }

    @SuppressLint("CheckResult")
    override fun fetchUser(username: String) {

        Observables.combineLatest(
            allUsers,
            api.getUser(username)
        )
            .take(1)
            .subscribeOn(Schedulers.io())
            .subscribe { pair ->
                val updatedSet = pair.first.apply {
                    add(pair.second.toUser())
                }
                allUsers.onNext(updatedSet)
            }


    }
}
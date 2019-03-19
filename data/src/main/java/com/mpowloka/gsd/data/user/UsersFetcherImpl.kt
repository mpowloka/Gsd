package com.mpowloka.gsd.data.user

import android.annotation.SuppressLint
import com.mpowloka.gsd.domain.user.UsersFetcher
import com.mpowloka.gsd.domain.user.UsersRepository
import io.reactivex.schedulers.Schedulers

class UsersFetcherImpl(
    private val usersRepository: UsersRepository,
    private val usersApi: UsersApi
) : UsersFetcher {

    @SuppressLint("CheckResult")
    override fun fetchUsers() {
        usersApi
            .getAllUsers()
            .subscribeOn(Schedulers.io())
            .take(1)
            .subscribe(
                { models ->
                    usersRepository.addUsers(
                        models.map { it.toUser() }
                    )
                },
                { exception ->
                    exception.printStackTrace()
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun fetchUser(username: String) {
        usersApi
            .getUser(username)
            .subscribeOn(Schedulers.io())
            .take(1)
            .subscribe(
                { model ->
                    usersRepository.addUser(model.toUser())
                },
                { exception ->
                    exception.printStackTrace()
                })
    }

}
package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import io.reactivex.Observable

class UsersRepositoryImpl(
    private val usersCache: UsersCache
) : UsersRepository {

    override fun getAllUsers(): Observable<List<User>> {
        return usersCache.getUsers()
    }

    override fun addUser(user: User) {
        usersCache.addUser(user)
    }

    override fun addUsers(users: List<User>) {
        usersCache.addUsers(users)
    }

}


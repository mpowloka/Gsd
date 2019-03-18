package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import io.reactivex.Observable

class UsersRepositoryImpl(
    private val api: UsersApi
) : UsersRepository {

    override fun getAllUsers(): Observable<List<User>> {
        return api.getAllUsers().map { models -> models.map { it.toUser() } }
    }

}
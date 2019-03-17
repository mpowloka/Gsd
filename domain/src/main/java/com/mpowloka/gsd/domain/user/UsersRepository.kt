package com.mpowloka.gsd.domain.user

import io.reactivex.Observable

interface UsersRepository {

    fun getAllUsers(): Observable<List<User>>

    fun getCurrentUser(): Observable<User>

    fun setCurrentUser(user: User)

}
package com.mpowloka.gsd.domain.user

import io.reactivex.Observable

interface UsersRepository {

    fun getAllUsers(): Observable<List<User>>

}
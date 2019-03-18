package com.mpowloka.gsd.domain.applicationstate

import com.mpowloka.gsd.domain.user.User
import io.reactivex.Observable

interface ApplicationStateRepository {

    fun getInitialUserSet(): Observable<Boolean>

    fun getCurrentUser(): Observable<User>

    fun setCurrentUser(user: User)

}
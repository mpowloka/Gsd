package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class UsersRepositoryImpl(
    private val api: UsersApi
) : UsersRepository {

    private val currentUserSubject = BehaviorSubject.create<User>()

    override fun getAllUsers(): Observable<List<User>> {
        return api.getAllUsers().map { models -> models.map { it.toUser() } }
    }

    override fun getCurrentUser(): Observable<User> {
        return currentUserSubject
    }

    override fun setCurrentUser(user: User) {
        currentUserSubject.onNext(user)
    }

}
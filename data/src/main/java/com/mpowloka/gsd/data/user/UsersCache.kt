package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class UsersCache {

    private val users = mutableSetOf<User>()
    private val usersSubject = BehaviorSubject.createDefault(users)

    fun addUser(user: User) {
        users.add(user)
        usersSubject.onNext(users)
    }

    fun getUsers(): Observable<List<User>> {
        return usersSubject.map { it.toList() }
    }

    fun addUsers(users: List<User>) {
        this.users.addAll(users)
        usersSubject.onNext(this.users)
    }
}
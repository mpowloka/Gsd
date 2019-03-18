package com.mpowloka.gsd.data.applicationstate

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.user.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ApplicationStateRepositoryImpl : ApplicationStateRepository {

    private val currentUserSubject = BehaviorSubject.create<User>()

    private val initialUserSetSubject = BehaviorSubject.createDefault(false)
    private var initialUserSetFlag = false

    override fun getInitialUserSet(): Observable<Boolean> {
        return initialUserSetSubject
    }

    override fun getCurrentUser(): Observable<User> {
        return currentUserSubject
    }

    override fun setCurrentUser(user: User) {
        currentUserSubject.onNext(user)

        synchronized(initialUserSetFlag) {
            if(!initialUserSetFlag) {
                initialUserSetFlag = true
                initialUserSetSubject.onNext(true)
            }
        }
    }
}
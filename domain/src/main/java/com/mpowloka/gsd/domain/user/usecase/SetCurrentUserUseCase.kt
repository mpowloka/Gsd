package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository

class SetCurrentUserUseCase(
    private val usersRepository: UsersRepository
) {

    fun set(user: User) {
        usersRepository.setCurrentUser(user)
    }
}
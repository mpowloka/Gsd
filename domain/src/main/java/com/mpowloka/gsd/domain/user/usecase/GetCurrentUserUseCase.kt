package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersRepository

class GetCurrentUserUseCase(
    private val usersRepository: UsersRepository
) {

    fun get() = usersRepository.getCurrentUser()

}
package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersRepository

class GetAllUsersUseCase(
    private val usersRepository: UsersRepository
) {

    fun get() = usersRepository.getAllUsers()
}
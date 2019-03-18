package com.mpowloka.gsd.domain.applicationstate.usecase

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.user.User

class SetCurrentUserUseCase(
    private val applicationStateRepository: ApplicationStateRepository
) {

    fun set(user: User) {
        applicationStateRepository.setCurrentUser(user)
    }
}
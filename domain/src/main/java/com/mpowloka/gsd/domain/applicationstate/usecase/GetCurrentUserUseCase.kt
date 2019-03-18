package com.mpowloka.gsd.domain.applicationstate.usecase

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository

class GetCurrentUserUseCase(
    private val applicationStateRepository: ApplicationStateRepository
) {

    fun get() = applicationStateRepository.getCurrentUser()

}